package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.TimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.TimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.web.SecurityUtil.*;

public class MealServlet extends HttpServlet {
//   private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);
//   private MealRepository repository;
//
//    @Override
//    public void init() throws ServletException {
//        repository = new InMemoryMealRepository();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        logger.debug("Get method. Forward to meals.jsp");
//        String action = req.getParameter("action");
//        Meal meal;
//        switch (action = action==null? "all" : action){
//            case "create":
//            case "update":
//                meal = action.equals("create") ?
//                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),"",1000) :
//                        repository.get(getId(req), authUserId());
//                req.setAttribute("meal",meal);
//                req.getRequestDispatcher("/mealForm.jsp").forward(req,resp);
//                break;
//            case "delete":
//                int id = getId(req);
//                logger.info("Delete {}", id);
//                repository.delete(id, authUserId());
//                resp.sendRedirect("meals");
//
//            case "filter":
//                LocalDate startDate = parseLocalDate(req.getParameter("startDate"));
//                LocalDate endDate = parseLocalDate(req.getParameter("endDate"));
//                LocalTime startTime = parseLocalTime(req.getParameter("startTime"));
//                LocalTime endTime = parseLocalTime(req.getParameter("endTime"));
//                req.setAttribute("meals", getTos(repository.getInInterval(authUserId(), LocalDateTime.of(startDate, startTime),
//                        LocalDateTime.of(endDate, endTime)), authUserCaloriesPerDay()));
//                req.getRequestDispatcher("/meals.jsp").forward(req, resp)   ;
//                break;
//            case "all":
//            default:
//                req.setAttribute("meals", getTos(repository.getAll(authUserId()), DEFAULT_CALORIES_PER_DAY));
//                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        logger.debug("Post method. Add/update a meal");
//        req.setCharacterEncoding("UTF-8");
//        String id = req.getParameter("id");
//        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
//        String description = req.getParameter("description");
//        Integer calories = Integer.valueOf(req.getParameter("calories"));
//
//        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id), dateTime, description, calories);
//        repository.save(meal, authUserId());
//        resp.sendRedirect("meals");
//
//    }
//
//    private int getId(HttpServletRequest req){
//        String id = Objects.requireNonNull(req.getParameter("id"));
//        return Integer.parseInt(id);
//    }
}
