package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.Classroom;
import com.lz.manage.model.vo.classroom.ClassroomVo;
import com.lz.manage.model.dto.classroom.ClassroomQuery;
import com.lz.manage.model.dto.classroom.ClassroomInsert;
import com.lz.manage.model.dto.classroom.ClassroomEdit;
import com.lz.manage.service.IClassroomService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 教室信息Controller
 *
 * @author YY
 * @date 2026-01-14
 */
@RestController
@RequestMapping("/manage/classroom")
public class ClassroomController extends BaseController
{
    @Resource
    private IClassroomService classroomService;

    /**
     * 查询教室信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:classroom:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClassroomQuery classroomQuery)
    {
        Classroom classroom = ClassroomQuery.queryToObj(classroomQuery);
        startPage();
        List<Classroom> list = classroomService.selectClassroomList(classroom);
        List<ClassroomVo> listVo= list.stream().map(ClassroomVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出教室信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:classroom:export')")
    @Log(title = "教室信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClassroomQuery classroomQuery)
    {
        Classroom classroom = ClassroomQuery.queryToObj(classroomQuery);
        List<Classroom> list = classroomService.selectClassroomList(classroom);
        ExcelUtil<Classroom> util = new ExcelUtil<Classroom>(Classroom.class);
        util.exportExcel(response, list, "教室信息数据");
    }

    /**
     * 获取教室信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:classroom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Classroom classroom = classroomService.selectClassroomById(id);
        return success(ClassroomVo.objToVo(classroom));
    }

    /**
     * 新增教室信息
     */
    @PreAuthorize("@ss.hasPermi('manage:classroom:add')")
    @Log(title = "教室信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClassroomInsert classroomInsert)
    {
        Classroom classroom = ClassroomInsert.insertToObj(classroomInsert);
        return toAjax(classroomService.insertClassroom(classroom));
    }

    /**
     * 修改教室信息
     */
    @PreAuthorize("@ss.hasPermi('manage:classroom:edit')")
    @Log(title = "教室信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ClassroomEdit classroomEdit)
    {
        Classroom classroom = ClassroomEdit.editToObj(classroomEdit);
        return toAjax(classroomService.updateClassroom(classroom));
    }

    /**
     * 删除教室信息
     */
    @PreAuthorize("@ss.hasPermi('manage:classroom:remove')")
    @Log(title = "教室信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(classroomService.deleteClassroomByIds(ids));
    }
}
