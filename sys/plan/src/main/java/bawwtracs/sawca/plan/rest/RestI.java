package bawwtracs.sawca.plan.rest;

import bawwtracs.sawca.plan.entity.Plan;
import bawwtracs.sawca.plan.entity.Schedule;
import bawwtracs.sawca.plan.entity.Step;
import bawwtracs.sawca.plan.service.IPlanService;
import bawwtracs.sawca.plan.service.IScheduleService;
import bawwtracs.sawca.plan.service.IStepService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/plan")
public class RestI {

    @Resource
    private IPlanService planService;
    @Resource
    private IStepService stepService;
    @Resource
    private IScheduleService scheduleService;

    @PostMapping("/plan")
    public Plan createPlan(Plan plan) {
        return null;
    }

    @GetMapping("/plans")
    public List<Plan> retrievePlans(Integer limit, Integer offset) {
        return null;
    }

    @DeleteMapping("plan")
    public Long deletePlan(Long id) {
        return null;
    }

    @PostMapping("step")
    public Step createStep(Step step) {
        return null;
    }

    @GetMapping("steps")
    public List<Step> retrieveSteps(Integer limit, Integer offset) {
        return null;
    }

    @DeleteMapping("step")
    public Long deleteStep(Long id) {
        return null;
    }

    @PostMapping("schedule")
    public Schedule createSchedule(Schedule schedule) {
        return null;
    }

    @GetMapping("schedules")
    public List<Schedule> retrieveSchedules(Integer limit, Integer offset) {
        return null;
    }

    @DeleteMapping("schedule")
    public Long deleteSchedule(Long id) {
        return null;
    }
}
