package com.xy.admx.service;

import com.xy.cms.entity.AppUser;

public interface ApiAuthService {
	Boolean isAuth(AppUser user, Long apiId);

}
