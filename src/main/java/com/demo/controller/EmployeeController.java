package com.demo.controller;

import com.demo.entity.Employee;
import com.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee";
    }

    @GetMapping("/search")
    public String searchEmployees(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String position,
            Model model) {

        model.addAttribute("employees", employeeService.searchEmployees(employeeId, name, position));
        return "employee";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id)
                .orElseThrow(() -> new IllegalArgumentException("无效的员工ID: " + id)));
        return "employee";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        try {
            employeeService.validateEmployee(employee);
            employeeService.saveEmployee(employee);
            redirectAttributes.addFlashAttribute("success", "员工信息保存成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/api/employee/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteEmployee(id);
            redirectAttributes.addFlashAttribute("success", "员工删除成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除员工失败: " + e.getMessage());
        }
        return "redirect:/api/employee/list";
    }

    // 添加职位选项到模型
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("positionOptions", List.of(
                "经理", "前台接待", "客房服务", "维修人员", "安保人员", "厨师", "服务员"
        ));
    }
}