package utils;

public class NutritionCalculator {

 
    public static int calculateExpectedCarbs(
            boolean isMale,
            int age,
            double weightLbs,
            int heightFeet,
            int heightInches,
            double activityFactor,
            double carbPercent
    ) {
        // Convert to metric
        double weightKg = weightLbs * 0.453592;
        double heightCm = ((heightFeet * 12) + heightInches) * 2.54;

        // Mifflin-St Jeor BMR
        double bmr = 
            (10 * weightKg) + 
            (6.25 * heightCm) -
            (5 * age) + 
            (isMale ? 5 : -161);

        double tdee = bmr * activityFactor;

  
        double carbCalories = tdee * carbPercent;

        return (int) Math.round(carbCalories / 4.0);
    }
}
