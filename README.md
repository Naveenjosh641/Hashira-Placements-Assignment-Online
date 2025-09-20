# 🔐 Hashira Placements Assignment — Secret Sharing via Polynomial Interpolation

> ✅ **Secret Reconstructed**: 203948029348029384029384029384

A Java solution to reconstruct a secret integer using **Lagrange interpolation** over points given in mixed bases — based on **Shamir’s Secret Sharing Scheme**.

Submitted by: **Naveen Josh**

---

## 🎯 Problem

Given `n` points `(x, f(x))`, where each `f(x)` is provided as a string in a specified base (e.g., base 3, 16, etc.), reconstruct the secret `f(0)` using any `k` points.

---

## 🧠 Solution

- Parse `input.json`
- Convert each value from its base to `BigInteger`
- Use Lagrange interpolation on first `k` sorted points
- Output `f(0)` — the secret

✅ Uses exact integer arithmetic — no floating point  
✅ Handles arbitrarily large numbers  
✅ Supports bases 2–36

---

## 🚀 How to Run

### With Maven:
```bash
mvn compile
mvn exec:java
