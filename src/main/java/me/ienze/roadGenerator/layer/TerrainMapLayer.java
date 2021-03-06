package me.ienze.roadGenerator.layer;

import com.flowpowered.noise.model.Plane;
import com.flowpowered.noise.module.modifier.Abs;
import com.flowpowered.noise.module.modifier.Clamp;
import com.flowpowered.noise.module.source.Perlin;
import me.ienze.twoDimMap.MapLayer;
import me.ienze.twoDimMap.Vec;

import java.awt.Color;
import java.util.Random;

public class TerrainMapLayer implements MapLayer<Float> {

    private final Plane perlinPlane;
    private Vec size;

    public TerrainMapLayer(int width, int height, float frequency) {
        this(width, height, frequency, new Random());
    }

    public TerrainMapLayer(int width, int height, float frequency, Random random) {
        this(width, height, frequency, random.nextInt());
    }

    public TerrainMapLayer(int width, int height, float frequency, int seed) {
        this(new Vec(width, height), frequency, seed);
    }

    public TerrainMapLayer(Vec size, float frequency, int seed) {
        this.size = size;

        Perlin perlin = new Perlin();
        perlin.setFrequency(frequency);
        perlin.setSeed(seed);

        Abs abs = new Abs();
        abs.setSourceModule(0, perlin);

        Clamp clamp = new Clamp();
        clamp.setLowerBound(0.0);
        clamp.setUpperBound(1.0);
        clamp.setSourceModule(0, abs);

        this.perlinPlane = new Plane(clamp);
    }

    @Override
    public Vec getSize() {
        return size;
    }

    @Override
    public int getWidth() {
        return size.x;
    }

    @Override
    public int getHeight() {
        return size.y;
    }

    @Override
    public Float get(int x, int y) {
        return (float) perlinPlane.getValue(x, y);
    }

    @Override
    public void set(int x, int y, Float value) {
        throw new UnsupportedOperationException("Perlin noise layer cannot by modified");
    }

    @Override
    public Color getColor(int x, int y) {
        float value = get(x, y);
        return new Color(value, value, value);
    }

    @Override
    public void setColor(int x, int y, Color color) {
        throw new UnsupportedOperationException("Perlin noise layer cannot by modified");
    }
}
