package com.bric.kagdatabkt.entry;

import java.util.List;

/**
 * Created by joyopeng on 17-10-9.
 */

public class RegisterResult extends ResultEntry<RegisterResult.Item> {

  public  class Item {
        public User User;
        public Token Token;
    }

    public class User {
        public String id;
        public String username;
        public String group_id;
        public String district_id;
        public String ipv4;
        public String created;
        public String modified;
        public String appkey;
    }

    public class Token {
        public String access_token;
        public Long expires_in;
        public String token_type;
        public String scope;
        public String refresh_token;
    }

}
