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
import com.lz.manage.model.domain.LectureAudit;
import com.lz.manage.model.vo.lectureAudit.LectureAuditVo;
import com.lz.manage.model.dto.lectureAudit.LectureAuditQuery;
import com.lz.manage.model.dto.lectureAudit.LectureAuditInsert;
import com.lz.manage.model.dto.lectureAudit.LectureAuditEdit;
import com.lz.manage.service.ILectureAuditService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 讲座审核Controller
 *
 * @author YY
 * @date 2026-01-14
 */
@RestController
@RequestMapping("/manage/lectureAudit")
public class LectureAuditController extends BaseController
{
    @Resource
    private ILectureAuditService lectureAuditService;

    /**
     * 查询讲座审核列表
     */
    @PreAuthorize("@ss.hasPermi('manage:lectureAudit:list')")
    @GetMapping("/list")
    public TableDataInfo list(LectureAuditQuery lectureAuditQuery)
    {
        LectureAudit lectureAudit = LectureAuditQuery.queryToObj(lectureAuditQuery);
        startPage();
        List<LectureAudit> list = lectureAuditService.selectLectureAuditList(lectureAudit);
        List<LectureAuditVo> listVo= list.stream().map(LectureAuditVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出讲座审核列表
     */
    @PreAuthorize("@ss.hasPermi('manage:lectureAudit:export')")
    @Log(title = "讲座审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LectureAuditQuery lectureAuditQuery)
    {
        LectureAudit lectureAudit = LectureAuditQuery.queryToObj(lectureAuditQuery);
        List<LectureAudit> list = lectureAuditService.selectLectureAuditList(lectureAudit);
        ExcelUtil<LectureAudit> util = new ExcelUtil<LectureAudit>(LectureAudit.class);
        util.exportExcel(response, list, "讲座审核数据");
    }

    /**
     * 获取讲座审核详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:lectureAudit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        LectureAudit lectureAudit = lectureAuditService.selectLectureAuditById(id);
        return success(LectureAuditVo.objToVo(lectureAudit));
    }

    /**
     * 新增讲座审核
     */
    @PreAuthorize("@ss.hasPermi('manage:lectureAudit:add')")
    @Log(title = "讲座审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LectureAuditInsert lectureAuditInsert)
    {
        LectureAudit lectureAudit = LectureAuditInsert.insertToObj(lectureAuditInsert);
        return toAjax(lectureAuditService.insertLectureAudit(lectureAudit));
    }

    /**
     * 修改讲座审核
     */
    @PreAuthorize("@ss.hasPermi('manage:lectureAudit:edit')")
    @Log(title = "讲座审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LectureAuditEdit lectureAuditEdit)
    {
        LectureAudit lectureAudit = LectureAuditEdit.editToObj(lectureAuditEdit);
        return toAjax(lectureAuditService.updateLectureAudit(lectureAudit));
    }

    /**
     * 删除讲座审核
     */
    @PreAuthorize("@ss.hasPermi('manage:lectureAudit:remove')")
    @Log(title = "讲座审核", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(lectureAuditService.deleteLectureAuditByIds(ids));
    }
}
