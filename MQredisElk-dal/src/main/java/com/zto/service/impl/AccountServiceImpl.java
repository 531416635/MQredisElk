package com.zto.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zto.dao.AccountDao;
import com.zto.model.Account;
import com.zto.service.AccountService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountDao accountDao;

	@Override
	public void insert(Account account) {
		// TODO Auto-generated method stub
		accountDao.insert(account);
	}

	@Override
	public List<Account> selectList(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return accountDao.selectList(map);
	}

}
