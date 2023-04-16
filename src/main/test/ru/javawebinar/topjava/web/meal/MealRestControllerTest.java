package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class MealRestControllerTest {
    @Test
    public void test() throws Exception {
        MealRepository mockRepository = mock(InMemoryMealRepository.class);
        //when(mockRepository.getAll(1, 2000)).thenReturn(MealsUtil.MEAL_LIST);

        MealRestController controller = new MealRestController();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setSingleView(new InternalResourceView("src/main/webapp/meals.jsp")).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/meals"))
                .andExpect(model().attributeExists("meals"));
    }

}