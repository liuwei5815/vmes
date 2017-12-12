package com.xy.cms.service;

import com.xy.cms.entity.License;

public interface LicenseService {
	public void save(License lic);
	public void update(License lic);
	public License getLicById(Long id);

}
