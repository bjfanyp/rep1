package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.Account;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.entity.basic.UserAccount;
import cn.fcars.infomgr.mapper.basic.AccountMapper;
import cn.fcars.infomgr.mapper.basic.UserAccountMapper;
import cn.fcars.infomgr.service.basic.AccountService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    Static aStatic;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    UserAccountMapper userAccountMapper;
    @Override
    public Account findByID(Integer id) {
        return accountMapper.findByID(id);
    }

    @Override
    public Account findByStringID(String id) {
        return null;
    }

    @Override
    public Account findByName(String name) {

        Account data=accountMapper.findByName(name);

        return data;
    }

    @Override
    public List<Account> findAll() {
        return accountMapper.findAll();
    }

    @Override
    public List<Account> findByQuery(BaseQuery accountQuery) {
        return accountMapper.findByQuery(accountQuery);
    }

    @Override
    public PageInfo<Account> findByPage(BaseQuery accountQuery) {
        Integer pageNum=1;
        if (accountQuery.getPageNum()!=null) {
            pageNum=accountQuery.getPageNum();
        }
        Integer pageSize = aStatic.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Account> accountList = findByQuery(accountQuery);
        PageInfo<Account> pageInfo = new PageInfo(accountList);
        return pageInfo;
    }

    @Override
    public Integer check(String name) {
        return accountMapper.check(name);
    }

    @Override
    public boolean checkExist(String id, String name) {
        return false;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Account account) throws RuntimeException {
        try {
            if (findByPara(account.getAccountName(), account.getAccountType()) != null) {
                throw new RuntimeException("已有此账号");
            }
            account.setIsDelete("0");
            accountMapper.add(account);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Account account) throws RuntimeException {
        try {
            Account data = findByID(account.getAccountID());
            data.setAccountType(account.getAccountType());
            data.setAccountBank(account.getAccountBank());
            data.setAccountNumber(account.getAccountNumber());
            accountMapper.update(data);
        }
      catch (Exception e)
      {
          e.printStackTrace();
          throw new RuntimeException(e.getMessage());
      }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByID(Integer id)  throws RuntimeException{
        try {
            Account account=accountMapper.findByID(id);
            account.setIsDelete("1");
            accountMapper.update(account);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Account findByPara(String name, String type) {
        return accountMapper.findByPara(name,type);
    }

    @Override
    public Account findAccount(String name, String bank, String number, String type) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operaAccountAndUserAccount(String id,Account account, User user) {
        Account acc = accountMapper.findAccount(account.getAccountName(),account.getAccountBank(),account.getAccountNumber(),account.getAccountType());
        if(acc==null) {
            accountMapper.add(account);
        }
        else {
            account.setAccountID(acc.getAccountID());
        }
        UserAccount userAccount =new UserAccount();
        if(id==null||id.equals("")){
            userAccount.setId(String.valueOf(new Date().getTime()));
            userAccount.setAccount(account);
            userAccount.setUser(user);
            userAccount.setIsDelete("0");
            userAccountMapper.add(userAccount);
        }
        else {
            userAccount = userAccountMapper.findByStringID(id);
            userAccount.setAccount(account);
            userAccount.setUser(user);
            userAccount.setIsDelete("0");
            userAccountMapper.update(userAccount);
        }
    }

    @Override
    public int delete(Account data) {
        return accountMapper.delete(data);
    }
}
