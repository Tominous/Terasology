/*
 * Copyright 2018 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.rendering.nui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controls the tabbing for widgets.
 */
public class TabbingManager {

    public static final int UNINITIALIZED_DEPTH = -9999;
    private static CoreScreenLayer openScreen;

    private static boolean widgetIsOpen;

    public static WidgetWithOrder focusedWidget;
    public static boolean focusSetThrough;

    private static int currentNum;
    private static int maxNum;
    private static int nextNum;
    private static ArrayList<Integer> usedNums;
    private static ArrayList<WidgetWithOrder> widgetsList;
    private static boolean initialized = false;

    public static void init() {
        widgetIsOpen = false;
        focusedWidget = null;
        focusSetThrough = false;
        currentNum = 0;
        maxNum = 0;
        nextNum = 0;
        usedNums = new ArrayList<>();
        widgetsList = new ArrayList<>();
        initialized = true;
    }

    public static void changeCurrentNum(boolean increase) {
        boolean loopedOnce = false;
        if (increase) {
            currentNum++;
        } else {
            currentNum--;
        }

        if (currentNum < 0 && usedNums.size() > 0) {
            currentNum = Collections.max(usedNums);
        }
        while (!usedNums.contains(currentNum)) {
            if (increase) {
                currentNum++;
            } else {
                currentNum--;
            }
            if (currentNum > maxNum) {
                if (!loopedOnce) {
                    currentNum = 0;
                    loopedOnce = true;
                } else {
                    break;
                }
            } else if (currentNum < 0) {
                currentNum = Collections.max(usedNums);
                loopedOnce = true;
            }
        }
    }
    public static int getNewNextNum() {
        nextNum++;
        maxNum++;
        while (usedNums.contains(nextNum)) {
            nextNum++;
            maxNum++;
        }
        return nextNum;
    }
    public static void addToUsedNums(int toAdd) {
        if (!usedNums.contains(toAdd)) {
            usedNums.add(toAdd);
            if (toAdd > maxNum) {
                maxNum = toAdd;
            }
        }
    }
    public static void addToWidgetsList(WidgetWithOrder widget) {
        if (!widgetsList.contains(widget)) {
            widgetsList.add(widget);
        }
    }
    public static void resetCurrentNum() {
        currentNum = 0;
    }
    public static int getCurrentNum() {
        return currentNum;
    }
    public static List<WidgetWithOrder> getWidgetsList() {
        return widgetsList;
    }
    public static boolean isInitialized() {
        return initialized;
    }
    public static void setInitialized(boolean setInit) {
        initialized = setInit;
    }
    public static boolean isWidgetOpen() {
        return widgetIsOpen;
    }
    public static CoreScreenLayer getOpenScreen() {
        return openScreen;
    }
    public static void setOpenScreen(CoreScreenLayer open) {
        openScreen = open;
    }
    public static void setWidgetIsOpen(boolean open) {
        widgetIsOpen = open;
    }
}
