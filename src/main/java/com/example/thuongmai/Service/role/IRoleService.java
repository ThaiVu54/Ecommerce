package com.example.thuongmai.Service.role;

import com.example.thuongmai.Service.IGeneralService;
import com.example.thuongmai.enums.EnumRoles;
import com.example.thuongmai.model.role.Role;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(EnumRoles name);
}
