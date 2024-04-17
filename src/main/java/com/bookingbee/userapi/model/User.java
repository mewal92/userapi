package com.bookingbee.userapi.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Data
@Setter
public class User {
     private String uid;
     private String email;
     private String password;
     private String name;

     public User() {}

     public User(String email, String name, String password){
          this.email = email;
          this.name = name;
          this.password = password;
     }



}


