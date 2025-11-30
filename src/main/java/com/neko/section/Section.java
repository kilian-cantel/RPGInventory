package com.neko.section;

import com.neko.RPGInventory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Section {

    private String title;
    private int id;
    private Step step1;
    private Step step2;
    private Step step3;
    private Step step4;
    private Step step5;
    private Step step6;
    private Section nextSection;

    public Section(@NotNull String title, int id, @Nullable Step step1, @Nullable Step step2, @Nullable Step step3, @Nullable Step step4, @Nullable Step step5, @Nullable Step step6, @Nullable Section nextSection) {
        this.title = title;
        this.id = id;
        this.step1 = step1;
        this.step2 = step2;
        this.step3 = step3;
        this.step4 = step4;
        this.step5 = step5;
        this.step6 = step6;
        this.nextSection = nextSection;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public Step getStep1() {
        return step1;
    }

    @Nullable
    public Step getStep2() {
        return step2;
    }

    @Nullable
    public Step getStep3() {
        return step3;
    }

    @Nullable
    public Step getStep4() {
        return step4;
    }

    @Nullable
    public Step getStep5() {
        return step5;
    }

    @Nullable
    public Step getStep6() {
        return step6;
    }

    @Nullable
    public Section getNextSection() {
        return nextSection;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public void setStep1(@Nullable Step step1) {
        this.step1 = step1;
    }

    public void setStep2(@Nullable Step step2) {
        this.step2 = step2;
    }

    public void setStep3(@Nullable Step step3) {
        this.step3 = step3;
    }

    public void setStep4(@Nullable Step step4) {
        this.step4 = step4;
    }

    public void setStep5(@Nullable Step step5) {
        this.step5 = step5;
    }

    public void setStep6(@Nullable Step step6) {
        this.step6 = step6;
    }

    public void setNextSection(@Nullable Section nextSection) {
        this.nextSection = nextSection;
    }

    public static Section loadSection(YamlConfiguration config) {
        return loadSection(1, config);
    }

    private static Section loadSection(int id, FileConfiguration config) {
        if (!config.contains(id + ".title")) return null;

        return new Section(
                Objects.requireNonNull(config.getString(id + ".title")),
                id,
                Step.loadStep(id, 1, config),
                Step.loadStep(id, 2, config),
                Step.loadStep(id, 3, config),
                Step.loadStep(id, 4, config),
                Step.loadStep(id, 5, config),
                Step.loadStep(id, 6, config),
                loadSection(id + 1, config)
        );
    }

    @Nullable
    public Step getBestUnlocked(Player player) {
        if (!this.step1.isFullUnlocked(player)) return null;
        if (!this.step2.isFullUnlocked(player)) return this.step1;
        if (!this.step3.isFullUnlocked(player)) return this.step2;
        if (!this.step4.isFullUnlocked(player)) return this.step3;
        if (!this.step5.isFullUnlocked(player)) return this.step4;
        if (!this.step6.isFullUnlocked(player)) return this.step5;
        return this.step6;
    }

    @NotNull
    public Section cloneSection() {
        return new Section(
                this.title,
                this.id,
                this.step1,
                this.step2,
                this.step3,
                this.step4,
                this.step5,
                this.step6,
                this.nextSection == null ? null : this.nextSection.cloneSection()
        );
    }

    @Nullable
    public Step getStep(int step) {
        return switch (step) {
            case 1 -> this.step1;
            case 2 -> this.step2;
            case 3 -> this.step3;
            case 4 -> this.step4;
            case 5 -> this.step5;
            case 6 -> this.step6;
            default -> null;
        };
    }

    @Nullable
    public static Step getStep(String stepString) {
        if (stepString == null) return null;

        String[] split = stepString.split(":");

        int id = Integer.parseInt(split[0]);
        int step = Integer.parseInt(split[1]);

        Section section = RPGInventory.getSection(id);
        if (section == null) return null;

        return section.getStep(step);
    }
}
