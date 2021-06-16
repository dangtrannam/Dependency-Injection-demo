/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fu.util;

import data.Student;

/**
 *
 * @author giao-lang
 */
public class FilterBy5 implements Filter<Student>{

    @Override
    public boolean check(Student obj) {
        if(obj.getGpa() >= 5)
            return true;
        return false;
    }
    //đây là bộ lọc sv >= 5 điểm, chấp chuyên ngành
    
}
