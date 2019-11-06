public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    static final double G = 6.67e-11;

    public Body(double xP, double yP, double xV,
                double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
    }

    public double calcDistance(Body b) {
        double dx = b.xxPos - this.xxPos;
        double dy = b.yyPos - this.yyPos;
        double r = Math.pow(dx * dx + dy * dy, 0.5);
        return r;
    }

    public double calcForceExertedBy(Body b) {
        double r = calcDistance(b);
        double F = G * this.mass * b.mass / (r * r);
        return F;
    }

    public double calcForceExertedByX(Body b) {
        double F = calcForceExertedBy(b);
        double r = calcDistance(b);
        double dx = b.xxPos - this.xxPos;
        double Fx = F * dx / r;
        return Fx;
    }

    public double calcForceExertedByY(Body b) {
        double F = calcForceExertedBy(b);
        double r = calcDistance(b);
        double dy = b.yyPos - this.yyPos;
        double Fy = F * dy / r;
        return Fy;
    }

    public double calcNetForceExertedByX(Body[] bodies) {
        double Fx = 0;
        for (Body b : bodies) {
            if (b.equals(this)) {
                continue;
            }
            Fx += calcForceExertedByX(b);
        }
        return Fx;
    }

    public double calcNetForceExertedByY(Body[] bodies) {
        double Fy = 0;
        for (Body b : bodies) {
            if (b.equals(this)) {
                continue;
            }
            Fy += calcForceExertedByY(b);
        }
        return Fy;
    }

    public void update(double dt, double Fx, double Fy) {
        double ax = Fx / mass;
        double ay = Fy / mass;
        xxVel += ax * dt;
        yyVel += ay * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
