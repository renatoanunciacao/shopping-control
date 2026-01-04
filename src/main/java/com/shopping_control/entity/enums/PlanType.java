package com.shopping_control.entity.enums;

public enum PlanType {
    FREE,
    PREMIUM,
    PARTNER
}

// Um PARTNER é um plano, não uma role
// 📌 Um USER pode ter plano FREE ou PREMIUM
// 📌 Um PARTNER pode ter plano PARTNER + role PARTNER

// | Usuário         | Role         | Plano                 |
// | --------------- | ------------ | --------------------- |
// | Cliente grátis  | ROLE_USER    | FREE                  |
// | Cliente premium | ROLE_USER    | PREMIUM               |
// | Mercado         | ROLE_PARTNER | PARTNER               |
// | Admin           | ROLE_ADMIN   | (ignorado ou interno) |
