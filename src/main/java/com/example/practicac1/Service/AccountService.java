package com.example.practicac1.Service;

public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws
    UsernameNotFoundException {
        return accountRepository
                .findByEmail(username)
                .orElseThrow();
    }
}
}
