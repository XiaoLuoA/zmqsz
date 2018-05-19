package cn.edu.vote.dto;

import cn.edu.vote.model.TeacherEntity;

/**
 * 转化TeacherEntity类
 *
 * @author zzu
 */
public class TeacherEntityDTO extends TeacherEntity {

    /**
     * 转化TeacherEntity类，num = num + delta， delta = 0
     * @param teacherEntity
     * 2018-03-21 lxy修改(由于TeacherEntity构造方法修改，导致此处的修改）
     */
    public TeacherEntityDTO(TeacherEntity teacherEntity) {
        super(teacherEntity.getId(), teacherEntity.getName(), teacherEntity.getDepartment(), teacherEntity.getPic(),
               teacherEntity.getDetail(), (teacherEntity.getNum() + teacherEntity.getDelta()),teacherEntity.getBlank1(),teacherEntity.getBlank2());

    }

    @Override
    public String toString() {
        return "TeacherEntityDTO{}";
    }
}
