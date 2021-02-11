package com.example.demo.controller;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    // display list of employees
    @GetMapping("/index")
    public String viewHomePage(Model model) {
        return findPaginated(1, "firstName", "asc", model);
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {

        if (result.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            return "new_employee";
        }
        // save employee to database
        employeeService.saveEmployee(employee);
        return "redirect:/index";

    }

    @Autowired
    EmployeeDao employeeDao;

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        employeeDao.updateEmployy();
        // get employee from the service
        Employee employee = employeeService.getEmployeeById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id, RedirectAttributes redirectAttributes) {

        // call delete employee method
        this.employeeService.deleteEmployeeById(id);


        redirectAttributes.addFlashAttribute("message", "Success");
        return "redirect:/index";
    }


    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Employee> listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listEmployees", listEmployees);
        return "index";
    }
}
