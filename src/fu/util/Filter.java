/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fu.util;

/**
 *
 * @author giao-lang
 */
//class này sẽ chơi với bất kì loại data/object nào ta mún
//các hàm trong này cũng chỉ cần ghi data type là T là đủ
//tùy lúc xài thực tế ta đưa vào object nào, máy tự sinh tham số
//data type theo loại data type ta đưa vào
//nếu ta ko xài T, thì hàm check() ta sẽ fix cứng với Student
//kĩ thuật này gọi là GENERIC, CHƠI VỚI DATA TYPE TỔNG QUÁT,
//T: TYPE
//V: VALUE

//từ Java 8 trở đi, nếu interface chỉ có 1 hàm duy nhất ko code
//người ta đặt cho nó 1 tên mới: FUNCTIONAL INTERFACER
//và nó sẽ đc dùng kết hợp với biểu thức LAMBDA
//biểu thức LAMBDA CHỈ CHƠI VÀ XUẤT HIỆN KHI CÓ FUNCTIONAL INTERFACE
//INTERFACE 1 HÀM ABSTRACT
//để cho xịn xò, người ta gõ thêm cái annotation, ko bắt buộc
@FunctionalInterface
public interface Filter<T> {
    
    public boolean check(T obj);
    
}

//chơi với Interface, có (3) cách:
//Interface là 1 class Cha, CLB chung hành động là đã cùng 1 nhà
//                          IS A MEMBER OF  
//Abstract class, Inheritance: bà con máu mủ họ hàng
//                             chung cả đặc điểm và hành động
//                             IS A KIND OF
//Vì interface là Cha/CLB, nên sẽ có Con, Con phải có code
//cách 1: class rời implements Cha
//cách 2: anonymous class
//cách 3: (functional interface): biểu thức lambda
