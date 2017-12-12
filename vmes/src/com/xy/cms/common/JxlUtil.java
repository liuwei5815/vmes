package com.xy.cms.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Excel工具�?
 * 	1、向Excel文档插入数据，可以是多行可以是多列，保留原单元格格式不变
 *	2、向Excel文档插入�?��新行，并且使用与上一行完全相同的格式
 *	3、更改sheet的名�?
 *	4、拷贝一个sheet，与原sheet内容完全�?��
 */
public class JxlUtil {
	
	private Workbook workBook;

	private WritableWorkbook wWorkBook;

	public JxlUtil(){
	}
	
	public void beginReadExcel(File excelFile)  throws BiffException, IOException{
		this.workBook = Workbook.getWorkbook(excelFile);
	}
	
	/**
	 * 创建Excel到输出流�?
	 * out 输出�?
	 * @param sheetNames sheet的名�?
	 * @throws BiffException
	 * @throws IOException
	 */
	public void beginCreateExcel(OutputStream out,String[] sheetNames)  throws BiffException, IOException{
		this.wWorkBook = Workbook.createWorkbook(out);
		for(int i = 0 ; i < sheetNames.length ; i++){
			this.wWorkBook.createSheet(sheetNames[i], i);
		}
	}
	
	/**
	 * 创建Excel到文�?
	 * @param xlsFile
	 * @param sheetNames sheet的名�?
	 * @throws BiffException
	 * @throws IOException
	 */
	public void beginCreateExcel(File xlsFile,String[] sheetNames)  throws BiffException, IOException{
		this.wWorkBook = Workbook.createWorkbook(xlsFile);
		for(int i = 0 ; i < sheetNames.length ; i++){
			this.wWorkBook.createSheet(sheetNames[i], i);
		}
	}
	
	/**
	 * 
	 * @param sheetNum
	 * @param fillRow	是否填充到一行上,false:填充到一列上
	 * @param startRowNum
	 * @param startColumnNum
	 * @param contents
	 * @throws BiffException
	 * @throws IOException
	 * @throws WriteException
	 */
	public void writeArrayToExcel(int sheetNum, boolean fillRow,
			int startRowNum, int startColumnNum, Object[] contents)
			throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		writeArrayToExcel(sheet, fillRow, startRowNum, startColumnNum, contents);
	}

	public void writeArrayToExcel(String sheetName, boolean fillRow,
			int startRowNum, int startColumnNum, Object[] contents)
			throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetName);
		writeArrayToExcel(sheet, fillRow, startRowNum, startColumnNum, contents);
	}

	private void writeArrayToExcel(WritableSheet sheet, boolean fillRow,
			int startRowNum, int startColumnNum, Object[] contents)
			throws WriteException, RowsExceededException {
		for (int i = 0, length = contents.length; i < length; i++) {
			int rowNum;
			int columnNum;
			if (fillRow) {
				rowNum = startRowNum;
				columnNum = startColumnNum + i;
			} else {
				rowNum = startRowNum + i;
				columnNum = startColumnNum;
			}
			this.writeToCell(sheet, rowNum, columnNum,convertString(contents[i]));
		}
	}

	public void writeArrayToExcelReplaceHtml(int sheetNum, boolean fillRow,
			int startRowNum, int startColumnNum, Object[] contents,
			boolean replaceHtml) throws BiffException, IOException,
			WriteException {
		if (replaceHtml) {
			for (int i = 0, length = contents.length; i < length; i++) {
				String value = convertString(contents[i]);
				contents[i] = replaceHtmlStr(value);
			}
		}
		this.writeArrayToExcel(sheetNum, fillRow, startRowNum, startColumnNum,contents);
	}

	public void writeArrayToExcelReplaceHtml(int sheetNum, boolean fillRow,
			int startRowNum, int startColumnNum, Object[] contents,
			boolean lineWrap, boolean replaceHtml) throws BiffException,
			IOException, WriteException {
		if (replaceHtml) {
			for (int i = 0, length = contents.length; i < length; i++) {
				String value = convertString(contents[i]);
				contents[i] = replaceHtmlStr(value);
			}
		}
		this.writeArrayToExcel(sheetNum, fillRow, startRowNum, startColumnNum,
				contents, lineWrap);
	}

	public void writeArrayToExcel(int sheetNum, boolean fillRow,
			int startRowNum, int startColumnNum, Object[] contents,
			boolean lineWrap) throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		writeArrayToExcel(sheet, fillRow, startRowNum, startColumnNum,contents, lineWrap);
	}

	private void writeArrayToExcel(WritableSheet sheet, boolean fillRow,
			int startRowNum, int startColumnNum, Object[] contents,
			boolean lineWrap) throws WriteException, RowsExceededException {
		for (int i = 0, length = contents.length; i < length; i++) {
			int rowNum;
			int columnNum;
			if (fillRow) {
				rowNum = startRowNum;
				columnNum = startColumnNum + i;
			} else {
				rowNum = startRowNum + i;
				columnNum = startColumnNum;
			}
			this.writeToCell(sheet, rowNum, columnNum,convertString(contents[i]), lineWrap);
		}
	}

	public void writeArrayToExcel(int sheetNum, boolean fillRow,
			String startColumnRowNum, Object[] contents) throws BiffException,
			IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		WritableCell startCell = sheet.getWritableCell(startColumnRowNum);
		int startRowNum = startCell.getRow();
		int startColumnNum = startCell.getColumn();
		this.writeArrayToExcel(sheetNum, fillRow, startRowNum, startColumnNum,contents);
	}

	public void writeToExcel(int sheetNum, int rowNum, int columnNum,
			Object value) throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		this.writeToCell(sheet, rowNum, columnNum, value);
	}

	public void writeToExcel(int sheetNum, String columnRowNum, Object value)
			throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		this.writeToCell(sheet, columnRowNum, value);
	}

	public void writeToExcel(String sheetName, String columnRowNum, Object value)
			throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetName);
		this.writeToCell(sheet, columnRowNum, value);
	}

	private void writeToCell(WritableSheet sheet, int rowNum, int columnNum,
			Object value) throws WriteException, RowsExceededException {
		WritableCell cell = sheet.getWritableCell(columnNum, rowNum);
		writeToCell(sheet, cell, value);
	}

	private void writeToCell(WritableSheet sheet, String columnRowNum,
			Object value) throws WriteException, RowsExceededException {
		WritableCell cell = sheet.getWritableCell(columnRowNum);
		writeToCell(sheet, cell, value);
	}

	private void writeToCell(WritableSheet sheet, WritableCell cell,
			Object value) throws WriteException, RowsExceededException {
		
		 // 用于标题
        /**WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 17,
                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
                jxl.format.Colour.WHITE);
        WritableCellFormat wcf_title = new WritableCellFormat(titleFont);
        wcf_title.setBackground(Colour.BLUE,Pattern.SOLID);
        wcf_title.setBorder(Border.ALL, BorderLineStyle.DOUBLE, Colour.BLACK);
        wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        wcf_title.setAlignment(Alignment.CENTRE);**/
		CellFormat cellFormat = cell.getCellFormat();
		Label label;
		if (cellFormat == null) {
			label = new Label(cell.getColumn(), cell.getRow(),convertString(value));
		} else {
			label = new Label(cell.getColumn(), cell.getRow(),convertString(value), cellFormat);
		}
		sheet.addCell(label);
	}

	public void writeToExcel(int sheetNum, int rowNum, int columnNum,
			Object value, boolean lineWrap) throws BiffException, IOException,
			WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		this.writeToCell(sheet, rowNum, columnNum, value, lineWrap);
	}

	public void writeToExcel(int sheetNum, String columnRowNum, Object value,
			boolean lineWrap) throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		this.writeToCell(sheet, columnRowNum, value, lineWrap);
	}

	public void writeToExcel(String sheetName, String columnRowNum,
			Object value, boolean lineWrap) throws BiffException, IOException,
			WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetName);
		this.writeToCell(sheet, columnRowNum, value, lineWrap);
	}

	private void writeToCell(WritableSheet sheet, int rowNum, int columnNum,
			Object value, boolean lineWrap) throws WriteException,
			RowsExceededException {
		WritableCell cell = sheet.getWritableCell(columnNum, rowNum);
		writeToCell(sheet, cell, value, lineWrap);
	}

	private void writeToCell(WritableSheet sheet, String columnRowNum,
			Object value, boolean lineWrap) throws WriteException,
			RowsExceededException {
		WritableCell cell = sheet.getWritableCell(columnRowNum);
		writeToCell(sheet, cell, value, lineWrap);
	}

	private void writeToCell(WritableSheet sheet, WritableCell cell,
			Object value, boolean lineWrap) throws WriteException,
			RowsExceededException {
		CellFormat cellFormat = cell.getCellFormat();
		Label label;
		if (cellFormat == null) {
			label = new Label(cell.getColumn(), cell.getRow(),
					convertString(value));
		} else {
			WritableCellFormat wCellFormat = new WritableCellFormat(cellFormat);
			wCellFormat.setWrap(lineWrap);
			label = new Label(cell.getColumn(), cell.getRow(),
					convertString(value), wCellFormat);
		}
		sheet.addCell(label);
	}

	public void insertRow(int sheetNum, int rowNum)
			throws RowsExceededException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		sheet.insertRow(rowNum);
	}

	public void insertRowWithFormat(int sheetNum, int rowNum, int columnSize)
			throws RowsExceededException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		insertRowWithFormat(sheet, rowNum, columnSize);
	}

	public void insertRowWithFormat(String sheetName, int rowNum, int columnSize)
			throws RowsExceededException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetName);
		insertRowWithFormat(sheet, rowNum, columnSize);
	}

	private void insertRowWithFormat(WritableSheet sheet, int rowNum,
			int columnSize) throws RowsExceededException, WriteException {
		sheet.insertRow(rowNum);
		sheet.setRowView(rowNum, sheet.getRowView(rowNum - 1));
		for (int i = 0; i < columnSize; i++) {
			CellFormat cellFormat = sheet.getCell(i, rowNum - 1).getCellFormat();
			if (cellFormat != null) {
				Label label = new Label(i, rowNum, "", cellFormat);
				sheet.addCell(label);
			}
		}
	}

	public void renameSheet(int sheetNum, String newName) throws IOException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		sheet.setName(newName);
	}

	public void renameSheet(String oldName, String newName) throws IOException {
		WritableSheet sheet = this.wWorkBook.getSheet(oldName);
		sheet.setName(newName);
	}

	public void copySheet(int oldSheetNum, String newSheetName) {
		this.wWorkBook.copySheet(oldSheetNum, newSheetName, oldSheetNum + 1);
	}

	public void copySheet(int oldSheetNum, int newSheetNum, String newSheetName) {
		this.wWorkBook.copySheet(oldSheetNum, newSheetName, newSheetNum);
	}

	public void endWrite() {
		if (this.wWorkBook != null) {
			try {
				this.wWorkBook.write();
			} catch (Exception e) {
			}
		}
		if (this.wWorkBook != null) {
			try {
				this.wWorkBook.close();
			} catch (Exception e) {
			}
		}
		if (this.workBook != null) {
			try {
				this.workBook.close();
			} catch (Exception e) {
			}
		}
	}
	
	public void endRead() {
		if (this.workBook != null) {
			try {
				this.workBook.close();
			} catch (Exception e) {
			}
		}
	}

	public static void fileCopy(String srcPath, String destPath)
			throws IOException {
		File destFile = new File(destPath);
		File destFileDir = destFile.getParentFile();
		if (destFileDir != null && !destFileDir.exists()) {
			destFileDir.mkdirs();
		}
		FileChannel srcChannel = new FileInputStream(srcPath).getChannel();
		FileChannel destChannel = new FileOutputStream(destPath).getChannel();
		try {
			srcChannel.transferTo(0, srcChannel.size(), destChannel);
		} finally {
			srcChannel.close();
			destChannel.close();
		}
	}

	public static String replaceHtmlStr(String str) {
		if (str.indexOf("&nbsp;") > -1) {
			str = str.replaceAll("&nbsp;", " ");
		}
		if (str.indexOf("<br/>") > -1) {
			str = str.replaceAll("<br/>", "\r\n");
		}
		if (str.indexOf("<br />") > -1) {
			str = str.replaceAll("<br />", "\r\n");
		}
		if (str.indexOf("</a>") > -1) {
			str = str.replaceAll("</a>", "");
		}
		int startIndex = -1;
		int endIndex = -1;
		while (str.indexOf("<a href=") > -1) {
			startIndex = str.indexOf("<a href=");
			endIndex = str.indexOf(">", startIndex);
			if (startIndex > -1 && endIndex > -1) {
				str = str.substring(0, startIndex)
						+ str.substring(endIndex + 1, str.length());
			}
		}
		return str;
	}

	private static String convertString(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}
	
	/**
	* 获取工作薄数�?
	* @return 工作薄数�?
	*/
	public int getNumberOfSheets(){
		return workBook == null ? 0 :workBook.getNumberOfSheets();
	}

	/**
	 * 获取工作薄�?行数
	 * @param sheet 工作�?
	 * @return 工作薄�?行数
	 */
	 public int getRows(Sheet sheet){
		 return sheet == null ?  0 : sheet.getRows();
	 }
	 /**
	 * 获取�?��列数
	 * @param sheet 工作�?
	 * @return 总行数最大列�?
	 */
	 public int getColumns(Sheet sheet){
		 return sheet == null ?  0 : sheet.getColumns();
	 }
	 /**
	 * 获取每行单元格数�?
	 * @param sheet 工作�?
	 * @param row 行数
	 * @return 每行单元格数�?
	 */
	 public Cell[] getRows(Sheet sheet,int row){
		 return sheet == null || sheet.getRows() < row ? null : sheet.getRow(row);
	 }
	 
	 /**
	 * 获取每行单元格数�?
	 * @param sheet 工作�?
	 * @param endrow 结束�?
	 * @param endCol 结束�?
	 * @return 每行单元格数�?
	 */
	 public Cell[][] getCells(Sheet sheet,int endrow,int endcol){
		 return getCells(sheet,0,endrow,0,endcol);
	 }
	 
	/**
	* 获取每行单元格数�?
	* @param sheet 工作�?
	* @param startrow 行数
	* @param endrow 结束�?
	* @param startcol �?���?
	* @param endCol 结束�?
	* @return 每行单元格数�?
	*/
	public Cell[][] getCells(Sheet sheet,int startrow,int endrow,int startcol,int endcol) {
		Cell[][] cellArray = new Cell[endrow-startrow][endcol-startcol];
		int maxRow = this.getRows(sheet);
		int maxCos = this.getColumns(sheet);
		for(int i = startrow ;i < endrow && i < maxRow ; i++){
			for(int j = startcol ; j < endcol && j < maxCos ; j++ ){
				cellArray[i-startrow][j-startcol] = sheet.getCell(j, i);
			}
		}  
		return cellArray;
	}
	
	/**
	* 得到行的�?
	* @param sheet
	* @param col
	* @param startrow
	* @param endrow
	* @return
	*/
	public Cell[] getColCells(Sheet sheet,int col,int startrow,int endrow){
		Cell[] cellArray = new Cell[endrow-startrow];
		int maxRow = this.getRows(sheet);
		int maxCos = this.getColumns(sheet);
		if(col <= 0 || col > maxCos || startrow > maxRow || endrow < startrow){
			return null;
		}
		if(startrow < 0){
			startrow = 0;
		}
		for(int i = startrow ;i < endrow && i < maxRow ; i++){
			cellArray[i-startrow] = sheet.getCell(col,i);
		}
		return cellArray;
	}
	
	/**
	* 得到列的�?
	* @param sheet
	* @param row
	* @param startcol
	* @param endcol
	* @return
	*/
	public Cell[] getRowCells(Sheet sheet,int row,int startcol,int endcol){
		Cell[] cellArray = new Cell[endcol-startcol];
		int maxRow = this.getRows(sheet);
		int maxCos = this.getColumns(sheet);
		if(row <= 0 || row > maxRow || startcol > maxCos || endcol < startcol){
			return null;
		}
		if(startcol < 0){
			startcol = 0;
		}
		for(int i = startcol ;i < startcol && i < maxCos ; i++){
			cellArray[i-startcol] = sheet.getCell(i,row);
		}
		return cellArray;
	}

	public Workbook getWorkBook() {
		return workBook;
	}

	public static void main(String[] args) throws Exception {
		/**File file = new File("c:\\stutemplate.xls");
		JxlUtil excel = new JxlUtil(file);
		try {
			excel.beginReadExcel();
			Workbook workBook = excel.getWorkBook();
			Sheet sheet0 = workBook.getSheet(0);
			int rows = excel.getRows(sheet0);
			int cols = excel.getColumns(sheet0);
			Cell[][] c = excel.getCells(sheet0,1,rows,0,cols);
			String f1 = null;
			for(int i =0 ; i < c.length;i++){//�?
				Cell[] obj = c[i];
				for(int j =0 ;j< obj.length; j++ ){//�?
					f1=obj[j].getContents().toString();
					System.out.println(i + "-" + j + ":" + f1);
				}
			}

		} finally {
			excel.endRead();
		}**/
		JxlUtil util = new JxlUtil();
		try{
			util.beginCreateExcel(new File("c:\\stutemplate.xls"), new String[]{"1","s2","s3","s4"});
			String[] contents = new String[]{"11111","22222","3333","4444","55555","66666"};
			util.writeArrayToExcel(0, true, 0, 0, contents);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			util.endWrite();
		}

//		try {
//			// 打开文件
//			WritableWorkbook book = Workbook.createWorkbook(new File("c:\\测试.xls"));
//
//			// 生成名为“第�?��”的工作表，参数0表示这是第一�?
//			WritableSheet sheet = book.createSheet("第一�?, 0);
//
//			// 在Label对象的构造子中指名单元格位置是第�?��第一�?0,0)
//			// 以及单元格内容为test
//			Label label = new Label(0, 0, "test");
//
//			// 将定义好的单元格添加到工作表�?
//			sheet.addCell(label);
//
//			/*
//			 * 生成�?��保存数字的单元格 必须使用Number的完整包路径，否则有语法歧义 单元格位置是第二列，第一行，值为789.123
//			 */
//			jxl.write.Number number = new jxl.write.Number(1, 0, 789.123);
//			sheet.addCell(number);
//
//			// 写入数据并关闭文�?
//			book.write();
//			book.close();
//
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		
	}
}
