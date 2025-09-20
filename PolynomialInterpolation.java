import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.math.BigInteger;
import java.util.*;

public class PolynomialInterpolation {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("input.json");
        Map<String, Object> input = mapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {});

        Map<String, Object> keys = (Map<String, Object>) input.get("keys");
        int k = ((Number) keys.get("k")).intValue();

        List<Point> points = new ArrayList<>();
        for (Map.Entry<String, Object> entry : input.entrySet()) {
            if (!entry.getKey().equals("keys")) {
                Map<String, String> data = (Map<String, String>) entry.getValue();
                int x = Integer.parseInt(entry.getKey());
                int base = Integer.parseInt(data.get("base"));
                String valueStr = data.get("value");
                BigInteger y = new BigInteger(valueStr, base);
                points.add(new Point(x, y));
            }
        }

        // Sort and take first k points
        points.sort(Comparator.comparingInt(p -> p.x));
        List<Point> selectedPoints = points.subList(0, k);

        BigInteger secret = lagrangeInterpolate(selectedPoints, 0);
        System.out.println("Secret: " + secret.toString());
    }

    private static BigInteger lagrangeInterpolate(List<Point> points, int targetX) {
        BigInteger result = BigInteger.ZERO;
        int n = points.size();

        for (int i = 0; i < n; i++) {
            BigInteger xi = BigInteger.valueOf(points.get(i).x);
            BigInteger yi = points.get(i).y;

            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for (int j = 0; j < n; j++) {
                if (i == j) continue;

                BigInteger xj = BigInteger.valueOf(points.get(j).x);
                numerator = numerator.multiply(BigInteger.valueOf(targetX).subtract(xj));
                denominator = denominator.multiply(xi.subtract(xj));
            }

            // Ensure exact division
            if (!numerator.mod(denominator).equals(BigInteger.ZERO)) {
                throw new ArithmeticException("Non-integer coefficient in Lagrange interpolation. Input may be invalid.");
            }

            BigInteger term = yi.multiply(numerator.divide(denominator));
            result = result.add(term);
        }

        return result;
    }

    static class Point {
        int x;
        BigInteger y;

        Point(int x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }
}
