package com.example.Expenses.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Category {
        private int id;
      private String name;
      private String color;
      private String backgroundColor;
      private String icon;


}
