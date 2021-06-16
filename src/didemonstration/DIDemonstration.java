/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package didemonstration;

import data.Student;
import data.StudentManager;
import fu.util.*;

/**
 *
 * @author giao-lang
 */
public class DIDemonstration {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        StudentManager sm = new StudentManager();
        sm.printAll();
        
        //in danh sách sv có điểm tb >=8
        System.out.println("The list of students with gpa >= 8");
        for (Student x : sm.getStudentsByGPA8AndUpper()) {
            x.showProfile();
        }
                
        //in danh sách sv có điểm tb >=8 and cn = SE
        System.out.println("The list of students with gpa >= 8 in SE");
        for (Student x : sm.getStudentsByGPA8AndUpperInSE()) {
            x.showProfile();
        }
        
        //in ds sv xài hàm gộp, đưa tham số loại filter
        System.out.println("The list of students with gpa >= 8 in SE");
        System.out.println("USING HUB-METHOD with type included");
        for (Student x : sm.getStudents(2)) {
            x.showProfile();
        }
        
        //XÀI DI QUA CLASS RỜI, CHÍCH CÁI WHERE VÀO TRONG MANAGER
        //                      INJECT
        System.out.println("Using DI - method 1 - concrete class");
        Filter by5 = new FilterBy5();
        //Khai Cha new Con, Khai Con new Con, Khai Cha new Cha - coi chừng
        //Khai Con new Cha - đổi ngành đi
        System.out.println("The list of students with gpa >= 5");
        for (Student x : sm.getStudents(by5)) {
            x.showProfile();
        }
        
        System.out.println("Using DI - method 2 - anonymous class");
        //mục tiêu của hàm getStudents() là cần 1 object thuộc
        //CLB Filter, và có code của hàm check()
        //vậy ta viết hàm thoy, ko care tên class implements Filter
        //2 cách viết Anonymous
        //2.1 khai báo biến object rời
        //2.1 new trực tiếp trong chỗ cần object
        Filter<Student> by5SE = new Filter<Student>() {
            @Override
            public boolean check(Student obj) {
                if (obj.getGpa() >= 5 && obj.getMajor().equalsIgnoreCase("SE"))
                    return true;
                return false;  //thằng nào xài else here, trừ điểm
            }
        };  //VIP  ; ~ new FilterBy5SE();
                  //do ta làm biếng tạo class rời rạc này
                  //đích đến cần hàm check() có code thoy mà
                  
        System.out.println("The list of students with gpa >= 5 && major = SE");
        for (Student x : sm.getStudents(by5SE)) {
            x.showProfile();
        }
        
        
        //HÙ, 2.1 new trực tiếp trong chỗ cần object, định luật bắc cầu
        //chỗ nào xài by5SE thì thay = new Filter() {....} luôn
        //vì bản chất là cần 1 cái new
        //viết kiểu này nhìn kinh lắ
        
        //tui mún in sv ngành ngôn ngữ Anh, EL, câu where mới
        System.out.println("The list of EL students");
        for (Student x : sm.getStudents(new Filter<Student>() {
            @Override
            public boolean check(Student obj) {
                if (obj.getMajor().equalsIgnoreCase("EL"))
                    return true;
                return false;
            }
        })) {
            x.showProfile();
        }
        
        //LAMBDA EXPRESSION
        //VÌ CLB FILTER CHỈ CÓ 1 HÀM ABSTRACT, MÚN XÀI NÓ THÌ 
        //VIẾT CODE CHO HÀM ABSTRACT,
        //TA DÙNG CLASS RỜI VIẾT CODE, TA DÙNG ANONYMOUS VIẾT CODE
        //VÀ TA CÓ THỂ THAY = BIỂU THỨC LAMBDA
        //NÃY GIỜ DÙ LÀM CLASS RỜI, HAY ANO, THÌ CŨNG CHỈ CÓ 1 HÀM
        //CẦN VIẾT CODE MÀ THOY, FOCUS VÀO CODE CỦA 1 HÀM
        //VẬY BỎ LUÔN TÊN HÀM CHO GỌN
        //MÌNH ĐÃ TỪNG BỎ ĐC TÊN CLASS QUA ANONYMOUS CLASS
        //NAY MÀY CHỈ CÓ ĐÚNG 1 HÀM, TAO KHỎI QUAN TÂM TÊN HÀM
        //FOCUS VÀO CODE CỦA HÀM, VẬY HÀM VÔ DANH, ANONYMOUS FUNCTION
        //TUI MÚN IN RA SV NGÀNH SS
        
        //Filter bySS = new Filter() {..... code gì đó của class Ano};
        Filter<Student> bySS = x -> {if (x.getMajor().equalsIgnoreCase("SS"))
                                                    return true;
                                                 return false;    
                                                };  
        System.out.println("The list of SS students");
        for (Student x : sm.getStudents(bySS)) {
            x.showProfile();
        }
        
        //Tự tin ko sửa code gốc hàm getStudents() 
        //mún query kiểu gì thì viết ra rồi gửi cho hàm getStudents()
        //chích xử lí của mình vào hàm kia, inject (v) injection (n)
        //      dependency - sự phụ thuộc, ràng buộc đc gài vào
        //thực tế của DI có thể bao gồm:
        //chích dependency qua hàm set(), chích qua constructor
        //chích qua 1 hàm bất kì - bài này của mình dùng kĩ thuật này
        
    //The last: tui mún in ra danh sách sv SS < 5
        Filter<Student> bySSLt5 = s -> {return s.getMajor().equalsIgnoreCase("SS") && s.getGpa() < 5;};
                               //check(Student s) {return true/false};
        System.out.println("The list of SS students with gpa < 5");
        for (Student x : sm.getStudents(bySSLt5)) {
            x.showProfile();
        }
    }
    
}

//ghi chú cho Lambda
//nếu hàm abstract ko có tham số, bạn chỉ cần ghi () -> thân hàm
//nếu hàm abstract có 1 tham số, bạn có thể ghi
//                             (kiểu-dữ-liệu biến) -> thân hàm
//                                           biến  -> thân hàm

//xét thân hàm
//             hàm nhiều lệnh bắt buộc -> {...code hàm abstract}
//                                     -> {... return như thường}
//             hàm 1 lệnh duy nhất, return hay ko ko quan trọng
//                                 -> return giá trị;
//                                 -> giá trị;
//                                 -> {return giá trị;}
//                                 -> { giá trị;} 
//nếu thân hàm 1 lệnh, thì ko cần {}
