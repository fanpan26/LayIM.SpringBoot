package com.fyp.layim.domain.mapper;

import com.fyp.layim.domain.BigGroup;
import com.fyp.layim.domain.FriendGroup;
import com.fyp.layim.domain.User;
import com.fyp.layim.domain.viewmodels.BigGroupViewModel;
import com.fyp.layim.domain.viewmodels.FriendGroupViewModel;
import com.fyp.layim.domain.viewmodels.UserViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author fyp
 * @crate 2017/11/4 12:29
 * @project SpringBootLayIM
 * @exception  No property named "userName" exists in source parameter(s).
 */
@Mapper
public interface LayimMapper {
    LayimMapper INSTANCE = Mappers.getMapper(LayimMapper.class);

    @Mapping(source = "userName", target = "username")
    UserViewModel mapUser(User user);
    List<UserViewModel> mapUser(List<User> user);

    @Mapping(source = "name",target = "groupname")
    FriendGroupViewModel mapFriendGroup(FriendGroup friendGroup);
    List<FriendGroupViewModel> mapFriendGroup(List<FriendGroup> friendGroups);

    @Mapping(source = "groupName",target = "groupname")
    BigGroupViewModel mapBigGroup(BigGroup bigGroup);
    List<BigGroupViewModel> mapBigGroup(List<BigGroup> bigGroups);

}
