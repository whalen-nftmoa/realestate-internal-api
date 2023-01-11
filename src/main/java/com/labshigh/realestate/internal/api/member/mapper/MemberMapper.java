package com.labshigh.realestate.internal.api.member.mapper;

import com.labshigh.realestate.internal.api.member.dao.MemberDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MemberMapper {

  void insert(MemberDao memberDao);

  void updateEmail(MemberDao memberDao);

  MemberDao get(MemberDao memberDao);


}
