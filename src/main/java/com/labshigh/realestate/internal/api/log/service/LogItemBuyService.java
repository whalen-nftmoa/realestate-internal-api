package com.labshigh.realestate.internal.api.log.service;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.log.dao.LogItemBuyDao;
import com.labshigh.realestate.internal.api.log.mapper.LogItemBuyMapper;
import com.labshigh.realestate.internal.api.log.model.request.LogItemBuyInsertRequestModel;
import com.labshigh.realestate.internal.api.log.model.request.LogItemBuyUpdateRequestModel;
import com.labshigh.realestate.internal.api.log.model.response.LogItemBuyResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Log4j2
@Service
public class LogItemBuyService {

  @Autowired
  private LogItemBuyMapper logItemBuyMapper;

  @Transactional
  public LogItemBuyResponseModel insertItemBuyLog(LogItemBuyInsertRequestModel requestModel) {
    LogItemBuyDao logItemBuyDao = LogItemBuyDao.builder()
      .memberUid(requestModel.getMemberUid())
      .status(Constants.LogStatus.PROGRESS.name())
      .marketItemUid(requestModel.getMarketItemUid())
        .build();

    logItemBuyMapper.insert(logItemBuyDao);


    return convertItemBuyLogResponseModel(logItemBuyDao);
  }

  public LogItemBuyResponseModel updateItemBuyLog(LogItemBuyUpdateRequestModel requestModel) {
    LogItemBuyDao logItemBuyDao = LogItemBuyDao.builder()
      .uid(requestModel.getUid())
      .code(requestModel.getCode())
      .message(requestModel.getMessage())
      .data(requestModel.getData())
      .itemBuyUid(requestModel.getItemBuyUid())
      .build();
    if( StringUtils.hasText(requestModel.getCode())){
      logItemBuyDao.setStatus(Constants.LogStatus.ERROR.name());
    }else if( requestModel.getItemBuyUid() > 0){
      logItemBuyDao.setStatus(Constants.LogStatus.COMPLETE.name());
    }
    logItemBuyMapper.update(logItemBuyDao);

    return convertItemBuyLogResponseModel(logItemBuyDao);
  }
  private LogItemBuyResponseModel convertItemBuyLogResponseModel(
      LogItemBuyDao dao) {

    if (dao == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    return LogItemBuyResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .marketItemUid(dao.getMarketItemUid())
        .memberUid(dao.getMemberUid())
        .itemBuyUid(dao.getItemBuyUid())
        .status(dao.getStatus())
        .code(dao.getCode())
        .message(dao.getMessage())
        .data(dao.getData())
        .build();
  }
}
