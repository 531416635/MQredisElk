package com.zto.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zto.model.Account;

public interface AccountService {
	void insert(Account account);
	
	List<Account> selectList(Map<String, Integer> map);
}
