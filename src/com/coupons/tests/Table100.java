package com.coupons.tests;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

// v1.0.4
// dont try printing list of primitives such as: String or Integer --- NO!
// prints only classes like beans with attributes/fields
// used example:
//	Table.print(myClass);
public class Table100 {
	// can add here field names you dont want to show
	// for example - List.of("id") wont show the field named 'id'
	public static List<String> noShowFields = new ArrayList<>(List.of()); // newly added

	public static void print(Object item) {
		if (item != null) {
			if (item.getClass().isPrimitive()) { // newlly added
				System.out.println(item);
			} else {
				print(List.of(item));
			}
		} else {
			System.out.println("null"); // newly added
		}
	}

	public static void print(List<?> collection) {
		if (collection.isEmpty()) {
			System.out.println("empty collection");
			return;
		}
		if (collection.get(0) == null) {
			System.out.println("null");
		}

		Class<?> itemClass = collection.get(0).getClass();

		List<List<String>> columns = new ArrayList<>();
		List<String> columnHeaders = new ArrayList<>();
		List<Integer> columnWidths = new ArrayList<>();

		Field[] fields = itemClass.getDeclaredFields();
		for (Field field : fields) {
			List<String> columnStrings = getFieldStringValues(field, collection);
			if (columnStrings == null || columnStrings.isEmpty()) {
				continue;
			}
			int columnWidth = Math.max(maxWidth(columnStrings), field.getName().length());

			columnHeaders.add(field.getName());
			columns.add(columnStrings);
			columnWidths.add(columnWidth);

		}

		int allWidth = 0;
		System.out.print("  ");
		for (int i = 0; i < columnHeaders.size(); i++) {
			String header = columnHeaders.get(i);
			int columnWidth = columnWidths.get(i);
			allWidth += columnWidth;
			System.out.print(padSpace(header, columnWidth));
			if (i < columnHeaders.size() - 1) {
				System.out.print(" | ");
				allWidth += 3;
			}
		}
		System.out.println();

		System.out.print("  ");
		System.out.println("-".repeat(allWidth));

		for (int rowIndex = 0; rowIndex < collection.size(); rowIndex++) {
			System.out.print("  ");
			for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
				String value = columns.get(columnIndex).get(rowIndex);
				int columnWidth = columnWidths.get(columnIndex);
				System.out.print(padSpace(value, columnWidth));
				if (columnIndex < columns.size() - 1) {
					System.out.print(" | ");
				}
			}
			System.out.println();
		}
		System.out.println("  " + "-".repeat(allWidth));
		System.out.println();

	}

	private static List<String> getFieldStringValues(Field field, List<?> objects) {
		List<String> values = new ArrayList<>();
		for (Object object : objects) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			field.setAccessible(true);

			if (noShowFields.contains(field.getName())) {
				continue;
			}
			if (object == null) {
				values.add("null");
				continue;
			}
			try {
				Object objectField = field.get(object);
				if (objectField == null) {
					values.add("null");
				} else {
					values.add(objectField.toString());
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
			}
		}

		return values;
	}

	private static int maxWidth(List<String> ls) {
		int width = 0;
		for (String string : ls) {
			width = Math.max(string.length(), width);
		}
		return width;
	}

	private static String padSpace(String str, int length) {
		StringBuilder builder = new StringBuilder(str);
		while (builder.length() <= length - 2) {
			builder.append(' ');
			builder.insert(0, ' ');
		}
		if (builder.length() < length) {
			builder.insert(0, ' ');
		}
		return builder.toString();
	}

}