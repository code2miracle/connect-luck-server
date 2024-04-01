package ac.kr.deu.connect.luck.food_truck;

import ac.kr.deu.connect.luck.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodTruckRepository extends JpaRepository<FoodTruck, Long> {
    List<FoodTruck> findByManager(User manager);

    List<FoodTruck> findByFoodType(FoodType foodType);

    List<FoodTruck> findByNameContaining(String name);

    List<FoodTruck> findByNameContainingAndFoodType(String name, FoodType foodType);

    void deleteByManager(User manager);
}
