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
import com.lz.manage.model.domain.Sign;
import com.lz.manage.model.vo.sign.SignVo;
import com.lz.manage.model.dto.sign.SignQuery;
import com.lz.manage.model.dto.sign.SignInsert;
import com.lz.manage.model.dto.sign.SignEdit;
import com.lz.manage.service.ISignService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 签到信息Controller
 *
 * @author YY
 * @date 2026-01-14
 */
@RestController
@RequestMapping("/manage/sign")
public class SignController extends BaseController
{
    @Resource
    private ISignService signService;

    /**
     * 查询签到信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:sign:list')")
    @GetMapping("/list")
    public TableDataInfo list(SignQuery signQuery)
    {
        Sign sign = SignQuery.queryToObj(signQuery);
        startPage();
        List<Sign> list = signService.selectSignList(sign);
        List<SignVo> listVo= list.stream().map(SignVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出签到信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:sign:export')")
    @Log(title = "签到信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SignQuery signQuery)
    {
        Sign sign = SignQuery.queryToObj(signQuery);
        List<Sign> list = signService.selectSignList(sign);
        ExcelUtil<Sign> util = new ExcelUtil<Sign>(Sign.class);
        util.exportExcel(response, list, "签到信息数据");
    }

    /**
     * 获取签到信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:sign:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Sign sign = signService.selectSignById(id);
        return success(SignVo.objToVo(sign));
    }

    /**
     * 新增签到信息
     */
    @PreAuthorize("@ss.hasPermi('manage:sign:add')")
    @Log(title = "签到信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SignInsert signInsert)
    {
        Sign sign = SignInsert.insertToObj(signInsert);
        return toAjax(signService.insertSign(sign));
    }

    /**
     * 修改签到信息
     */
    @PreAuthorize("@ss.hasPermi('manage:sign:edit')")
    @Log(title = "签到信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SignEdit signEdit)
    {
        Sign sign = SignEdit.editToObj(signEdit);
        return toAjax(signService.updateSign(sign));
    }

    /**
     * 删除签到信息
     */
    @PreAuthorize("@ss.hasPermi('manage:sign:remove')")
    @Log(title = "签到信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(signService.deleteSignByIds(ids));
    }
}
