package cn.edu.vote.service;


import cn.edu.vote.dao.TeacherDAO;
import cn.edu.vote.dto.TeacherEntityDTO;
import cn.edu.vote.model.TeacherEntity;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理Teacher的转化逻辑
 *
 * @author zzu
 */
public class TeacherEntityService {
    /**
     * 处理单个老师
     *
     * @param id 老师的ID
     * @return 转换的数据 TeacherEntityDTO
     */
    public static TeacherEntityDTO get(int id) {
        TeacherEntity teacherEntity = TeacherDAO.get(id);
        return new TeacherEntityDTO(teacherEntity);
    }

    /**
     * 处理全部老师
     *
     * @return 转换的数据 JSONArray
     */
    public static JSONArray get() {
        List<TeacherEntity> teachers = TeacherDAO.get();
        List<TeacherEntityDTO> teacherEntityDTOS = new ArrayList<>();
        teachers.forEach(teacherEntity -> teacherEntityDTOS.add(new TeacherEntityDTO(teacherEntity)));
        return new JSONArray(teacherEntityDTOS);
    }
}
