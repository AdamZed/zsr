package movement;

/**
 * @version 2.4
 */

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ToolsStage {

	static Color colour = Color.WHITE;
	static double gridSize = 40, drawSize = 2;
	private Stage toolsStage = new Stage();
	static int selected = -1;
	private static double initialX, initialY;
	private static boolean disableDrag = true;
	private MainWindow main;

	ToolsStage(MainWindow main) {
		this.main = main;
	}

	public Stage getStage() {

		toolsStage.setAlwaysOnTop(true);
		toolsStage.setTitle("Tools");
		toolsStage.setX(0);
		toolsStage.setY(Screen.getPrimary().getVisualBounds().getHeight() - 27);
		toolsStage.initStyle(StageStyle.UNDECORATED);
		toolsStage.setWidth(425);

		FlowPane buttonRoot = new FlowPane(Orientation.HORIZONTAL);
		buttonRoot.setStyle("-fx-background: #343434;");
		Scene buttonScene = new Scene(buttonRoot);

		Button grid = new Button(); // 0
		Button draw = new Button(); // 1
		Button line = new Button(); // 2
		Button angle = new Button(); // 3
		Button drag = new Button();
		Button save = new Button();
		ColorPicker cp = new ColorPicker();
		Slider sld = new Slider();

		// initiate slider
		sld.setSnapToPixel(true);
		sld.setSnapToTicks(true);
		sld.setShowTickMarks(true);
		sld.setMinorTickCount(1);
		sld.getStylesheets().add("/movement/slider_style.css");
		sld.setOnMouseDragged(me -> {
			if (selected == -1)
			{ } else if (selected == 0) {
				gridSize = sld.getValue();
				main.grid.redraw();
			} else {
				drawSize = sld.getValue();
				main.layers.tail.redraw();
			}
		});

		// grid button ID 0
		grid.setGraphic(Images.grid);
		grid.setStyle(Images.style);
		grid.setOnMouseEntered(me -> {
			if (selected != 0) {
				if (!GridTool.locked)
					grid.setGraphic(Images.gridMO);
			}

		});
		grid.setOnMouseExited(me -> {
			if (selected != 0) {
				if (!GridTool.locked)
					grid.setGraphic(Images.grid);
			}
		});
		grid.setOnMouseClicked(me -> {
			if (main.layers.tail != null) {
				if (main.layers.tail.blank) {
					main.layers.deleteTail();
					main.toolsPane.getChildren().remove(main.toolsPane.getChildren().size() - 1);
				} else
					main.layers.tail.disable();
			}
			if (selected != 0) {
				if (!GridTool.locked) {
					grid.setGraphic(Images.gridSel);
					main.grid.paintGrid();
				}
				draw.setGraphic(Images.draw);
				line.setGraphic(Images.line);
				angle.setGraphic(Images.angle);
				//

				selected = 0;
				updateSlider(sld);

			} else {
				if (GridTool.locked) {
					GridTool.locked = false;
					grid.setGraphic(Images.gridSel);
				} else {
					GridTool.locked = true;
					grid.setGraphic(Images.gridLoc);
				}
			}
			
		});

		// draw button ID 1
		draw.setGraphic(Images.draw);
		draw.setStyle(Images.style);
		draw.setOnMouseEntered(me -> {
			if (selected != 1)
				draw.setGraphic(Images.drawMO);
		});
		draw.setOnMouseExited(me -> {
			if (selected != 1)
				draw.setGraphic(Images.draw);
		});
		draw.setOnMouseClicked(me -> {
			
			if (main.layers.tail != null) {
				if (main.layers.tail.blank) {
					main.layers.deleteTail();
					main.toolsPane.getChildren().remove(main.toolsPane.getChildren().size() - 1);
				} else
					main.layers.tail.disable();
			}
			if (selected != 1) {
				if (!GridTool.locked)
					grid.setGraphic(Images.grid);
				draw.setGraphic(Images.drawSel);
				line.setGraphic(Images.line);
				angle.setGraphic(Images.angle);

				main.grid.clear();
				
				main.layers.newDraw();
				main.layers.tail.enable();
				main.toolsPane.getChildren().add(main.layers.tail.getNode());

				selected = 1;
				updateSlider(sld);
				
			} else {
				main.layers.newDraw();
				main.layers.tail.enable();
				main.toolsPane.getChildren().add(main.layers.tail.getNode());
			}
			
		});

		// line button ID 2
		line.setGraphic(Images.line);
		line.setStyle(Images.style);
		line.setOnMouseEntered(me -> {
			if (selected != 2)
				line.setGraphic(Images.lineMO);
		});
		line.setOnMouseExited(me -> {
			if (selected != 2)
				line.setGraphic(Images.line);
		});
		line.setOnMouseClicked(me -> {
			if (main.layers.tail != null) {
				if (main.layers.tail.blank) {
					main.layers.deleteTail();
					main.toolsPane.getChildren().remove(main.toolsPane.getChildren().size() - 1);
				} else
					main.layers.tail.disable();
			}
			if (selected != 2) {
				if (!GridTool.locked)
					grid.setGraphic(Images.grid);
				draw.setGraphic(Images.draw);
				line.setGraphic(Images.lineSel);
				angle.setGraphic(Images.angle);

				main.grid.clear();
				main.layers.newLine();
				main.layers.tail.enable();
				main.toolsPane.getChildren().add(main.layers.tail.getNode());

				selected = 2;
				updateSlider(sld);
			} else {
				main.layers.newLine();
				main.layers.tail.enable();
				main.toolsPane.getChildren().add(main.layers.tail.getNode());
			}
			
		});

		// angle button ID 3
		angle.setGraphic(Images.angle);
		angle.setStyle(Images.style);
		angle.setOnMouseEntered(me -> {
			if (selected != 3)
				angle.setGraphic(Images.angleMO);
		});
		angle.setOnMouseExited(me -> {
			if (selected != 3)
				angle.setGraphic(Images.angle);
		});
		angle.setOnMouseClicked(me -> {
			updateSlider(sld);
			if (main.layers.tail != null) {
				if (main.layers.tail.blank) {
					main.layers.deleteTail();
					main.toolsPane.getChildren().remove(main.toolsPane.getChildren().size() - 1);
				} else
					main.layers.tail.disable();
			}
			if (selected != 3) {
				if (!GridTool.locked)
					grid.setGraphic(Images.grid);
				draw.setGraphic(Images.draw);
				line.setGraphic(Images.line);
				angle.setGraphic(Images.angleSel);

				main.grid.clear();
				main.layers.newAngle();
				main.layers.tail.enable();
				main.toolsPane.getChildren().add(main.layers.tail.getNode());

				selected = 3;
				updateSlider(sld);
				
			} else {
				main.layers.newAngle();
				main.layers.tail.enable();
				main.toolsPane.getChildren().add(main.layers.tail.getNode());
			}
		});
		
		// save button
		save.setGraphic(Images.save);
		save.setStyle(Images.style);
		save.setOnMouseEntered(me -> save.setGraphic(Images.saveMO));
		save.setOnMouseExited(me -> save.setGraphic(Images.save));
		save.setOnMouseClicked(me -> main.savePNG(main.fileChooser, main.mainRoot));

		drag.setGraphic(Images.drag);
		drag.setStyle(Images.style);
		drag.setOnMousePressed(mp -> {
			initialX = mp.getSceneX();
			initialY = mp.getSceneY();
		});
		drag.setOnMouseDragged(md -> {
			toolsStage.setX(toolsStage.getX() + md.getSceneX() - initialX);
			toolsStage.setY(toolsStage.getY() + md.getSceneY() - initialY);
		});

		cp.getStyleClass().add("button");
		cp.setStyle("-fx-color-label-visible: false;" + "-fx-background-color: null;" + "-fx-background-color: null;");

		cp.setOnAction(ea -> {
			colour = cp.getValue();
			if (selected == 0)
				main.grid.paintGrid();
			else {
				if (main.layers.tail != null)
					main.layers.tail.redraw();
			}
				
		});

		buttonRoot.getChildren().addAll(grid, draw, line, angle, cp, sld, save);
		if (!disableDrag)
			buttonRoot.getChildren().add(drag);

		buttonRoot.autosize();
		toolsStage.setScene(buttonScene);

		return toolsStage;
	}
	
	private void updateSlider(Slider sld) {
		
		if (selected == -1)
		{ }
		else if (selected == 0) { // grid
			sld.setMajorTickUnit(10);
			sld.setMin(10);
			sld.setMax(100);
			sld.setValue(gridSize);
		} else { // others
			sld.setMin(1);
			sld.setMajorTickUnit(1);
			sld.setMax(10);
			sld.setValue(drawSize);
		}
		
	}
}