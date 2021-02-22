import javax.ejb.Stateless;

@Stateless
public class Calculate{
    
    public double calculateBMI(Double height, Double weight) {
        return weight / Math.pow(height, 2);
    }
}