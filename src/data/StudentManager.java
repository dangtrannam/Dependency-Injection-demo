/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import fu.util.Filter;
import java.util.*;

/**
 *
 * @author giao-lang
 */
public class StudentManager {

    Filter<Student> filter; //chờ chích vào sau
    
    private List<Student> stdList = new ArrayList();

    {  //floating-block of code, nằm trôi nổi ko thuộc hàm nào
        stdList.add(new Student("SE999999", "Chín Lê", 1999, 9.0, "SE"));
        stdList.add(new Student("SE888888", "Tám Trần", 1998, 8.0, "SE"));
        stdList.add(new Student("SE111111", "Một Phạm", 1991, 1.0, "EL"));
        stdList.add(new Student("SE444444", "Bốn Nguyễn", 1994, 4.0, "EL"));
        stdList.add(new Student("SE555555", "Năm Võ", 1995, 10.0, "SS"));
        stdList.add(new Student("SE333333", "Ba Lý", 2003, 3.0, "SS"));
    }

    //select *
    public void printAll() {
        System.out.println("The student list");
        for (Student x : stdList) {
            x.showProfile();
        }
    }

    //mình sẽ làm các hàm để trả về danh sách sinh viên theo tiêu
    //chí nào đó, filter, condition 
    //ví dụ: ds sv dtb >= 8
    //             dtb < 5
    //             major SE
    //             yob = 1995
    //             và còn n loại filter khác, mình phải đối diện
    //if bao nhiêu cho đủ
    //bao nhiêu hàm thì đủ. Truyền thống là ta ko thể tưởng tượng
    //đc đủ bộ lọc người dùng
    //         ví dụ: dssv của ngành SE IA mà có đtb >= 8
    //may ra mình chỉ có thể design sẵn filter cơ bản
    //cách LẦY LỘI - TƯ DUY BÌNH THƯỜNG, TRUYỀN THỐNG
    public List<Student> getStudentsByGPA8AndUpper() {
        List<Student> result = new ArrayList();
        for (Student x : stdList) {
            if (x.getGpa() >= 8) {
                result.add(x);
            }
        }
        return result;
    }

    //thêm where thêm hàm
    public List<Student> getStudentsByGPA8AndUpperInSE() {
        List<Student> result = new ArrayList();
        for (Student x : stdList) {
            if (x.getGpa() >= 8 && x.getMajor().equalsIgnoreCase("SE")) {
                result.add(x);
            }
        }
        return result;
    }

    //thêm where thì lại thêm hàm
    //có thể nào 1 hàm nhiều where đc ko?????
    //LẦY LỘI, dùng flag, DỒN TOÀN BỘ ĐÁM IF VÀO TRONG HÀM
    //có thể dùng enum {tập hợp các giá trị định sẵn}
    //quy ước ngầm: 
    //type 1: >= 8 for all
    //type 2: >= 8 for SE
    //type 3: sv of EL
    //type 4: sv of JP
    //type 5: ... vô chừng về mặt lí thuyết
    //gọn 1 hàm, nhưng việc hỗ trợ n filter là ko thể, vì vẫn phải 
    //sửa hàm    
    public List<Student> getStudents(int filterType) {

        List<Student> result = new ArrayList();

        switch (filterType) {
            case 1:  //>= 8
                for (Student x : stdList) {
                    if (x.getGpa() >= 8) {
                        result.add(x);
                    }
                }
                break;

            case 2:
                for (Student x : stdList) {
                    if (x.getGpa() >= 8 && x.getMajor().equalsIgnoreCase("SE")) {
                        result.add(x);
                    }
                }
                break;

            case 3:
                break;
        }
        //tương lai còn nhiều case
        return result;
    }

    //cách XỊN XÒ: CHỈ CẦN 1 HÀM, CHẤP TẤT CẢ CÁC FILTER - INTERFACE & DI
    //nếu tham số cho hàm getStudents(là con số)
    //thì trong hàm ta phải if else
    //sau nay nếu muốn thêm if else khác, ta phải thêm code ở chính hàm này
    //vậy ta vẫn phải sửa code, nhưng sửa mức ít nhất có thể 
    //theo hướng suy nghĩ sau
    //ban đưa cho tớ cái xử lí bạn muốn
    //tớ lấy xử lí của bạn đưa vào và gọi xử lí đó thoy
    //việc sửa đổi đẩy về phía bạn, tớ hàm getStudents() ko sửa code
    //do đó tớ nhận 1 hàm thay vì nhận 1 biến flag-cờ-int rồi tớ if else
    //tớ nhận hàm, tớ gọi hàm của cậu, xử lí là của cậu
    //để hàm linh hoạt mọi hoàn cảnh, hàm nên là abstract
    //ko có code, lúc nào cần xài hàm, viết code đi
    //đẩy/đưa cái object có hàm có code vào trong getStudents()
    //ta sẽ nhận vào INTERFACE với hàm abstract
    //lúc nào bạn mún where theo kiểu bất kì, bạn viết code cho hàm abs
    //rồi đẩy cho tớ
    //tớ làm việc duy nhất, gọi hàm bạn
    
    //DI bắt đầu
    public List<Student> getStudents(Filter condition) {
        
        List<Student> result = new ArrayList();
        //tớ sẽ duyệt qua danh sv tớ đang có giống như trên
        //lôi ra đc từng cháu x
        //đưa cháu x này cho hàm check(x) của object condition
        //hàm check() này sẽ trả về true, đúng sv x này thỏa đk 
        //và add x vào trong danh sách đc chọn
        for (Student x : stdList) {
            if(condition.check(x) == true)
                result.add(x);
        }
        return result;
    }
    
    //viết thử trong Manager, kiểm tra Lambda của Comparator
    //ta hãy nhìn Comparator chính là Interface giống Filter của mình
    //nó đc chích vào hàm Collections.sort(chích vào cách so sánh)
    //Filter đc chích vào hàm getStudents(chích vào điều kiện lọc)
    public void sortStudents() {
        //ta mún sort theo điểm chấp chuyên ngành
        Comparator<Student> cmp = (left, right) -> 
                 Double.compare(left.getGpa(), right.getGpa());
        Collections.sort(stdList, cmp);
        
        System.out.println("The student list sorted by gpa ascending");
        for (Student x : stdList) {
            x.showProfile();
        }
        
    }
    //chơi main() here!!!!!!!!
    public static void main(String[] args) {
        new StudentManager().sortStudents();
        System.gc();
    }   //object vô danh
        //hok xài biến object
        //xài xong bị giết liền JVM giết liền
    
    
}
