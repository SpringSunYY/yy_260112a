package com.lz.manage.controller;

import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.core.page.TableDataInfo;
import com.lz.common.enums.BusinessType;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.manage.model.domain.Lecture;
import com.lz.manage.model.dto.lecture.LectureEdit;
import com.lz.manage.model.dto.lecture.LectureInsert;
import com.lz.manage.model.dto.lecture.LectureQuery;
import com.lz.manage.model.enums.LectureStatusEnum;
import com.lz.manage.model.vo.lecture.LectureVo;
import com.lz.manage.service.ILectureService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 讲座信息Controller
 *
 * @author YY
 * @date 2026-01-14
 */
@RestController
@RequestMapping("/manage/lecture")
public class LectureController extends BaseController {
    @Resource
    private ILectureService lectureService;

    /**
     * 查询讲座信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:lecture:list')")
    @GetMapping("/list")
    public TableDataInfo list(LectureQuery lectureQuery) {
        Lecture lecture = LectureQuery.queryToObj(lectureQuery);
        startPage();
        List<Lecture> list = lectureService.selectLectureList(lecture);
        List<LectureVo> listVo = list.stream().map(LectureVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 查询讲座信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:lecture:list')")
    @GetMapping("/list/home")
    public TableDataInfo listHome(LectureQuery lectureQuery) {
        Lecture lecture = LectureQuery.queryToObj(lectureQuery);
        startPage();
        lecture.setStatus(LectureStatusEnum.LECTURE_STATUS_2.getValue());
        List<Lecture> list = lectureService.selectLectureListHome(lecture);
        List<LectureVo> listVo = list.stream().map(LectureVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出讲座信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:lecture:export')")
    @Log(title = "讲座信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LectureQuery lectureQuery) {
        Lecture lecture = LectureQuery.queryToObj(lectureQuery);
        List<Lecture> list = lectureService.selectLectureList(lecture);
        ExcelUtil<Lecture> util = new ExcelUtil<Lecture>(Lecture.class);
        util.exportExcel(response, list, "讲座信息数据");
    }

    /**
     * 获取讲座信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:lecture:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        Lecture lecture = lectureService.selectLectureById(id);
        return success(LectureVo.objToVo(lecture));
    }

    /**
     * 新增讲座信息
     */
    @PreAuthorize("@ss.hasPermi('manage:lecture:add')")
    @Log(title = "讲座信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LectureInsert lectureInsert) {
        Lecture lecture = LectureInsert.insertToObj(lectureInsert);
        return toAjax(lectureService.insertLecture(lecture));
    }

    /**
     * 修改讲座信息
     */
    @PreAuthorize("@ss.hasPermi('manage:lecture:edit')")
    @Log(title = "讲座信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LectureEdit lectureEdit) {
        Lecture lecture = LectureEdit.editToObj(lectureEdit);
        return toAjax(lectureService.updateLecture(lecture));
    }

    /**
     * 删除讲座信息
     */
    @PreAuthorize("@ss.hasPermi('manage:lecture:remove')")
    @Log(title = "讲座信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(lectureService.deleteLectureByIds(ids));
    }
}
