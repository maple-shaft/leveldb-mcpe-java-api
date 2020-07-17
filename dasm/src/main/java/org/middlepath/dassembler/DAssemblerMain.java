package org.middlepath.dassembler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Callable;
import org.apache.commons.io.FileUtils;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(
		name="dassembler",
		description="An assembler for the DAssembler language for Redstone Computers.",
		mixinStandardHelpOptions = true,
		version="0.1"
)
public class DAssemblerMain implements Callable<Integer> {

	public static final InputStream is;
	public static Properties props;
	
	static {
		//make this use the file argument.
		is = DAssemblerMain.class.getResourceAsStream("/org/middlepath/fibonacci.dasm");
		props = new Properties();
		try {
			props.load(new FileReader(new File("put a properties file here")));
		} catch (FileNotFoundException  e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Parameters(index="0", description="The source file to assemble")
	private File sourceFile;
	
	@Parameters(index="1", description="The output binary file.")
	private File destFile;
	
	public static void main(String[] args) throws IOException {
		int exitCode = new CommandLine(new DAssemblerMain()).execute(args);
		System.exit(exitCode);
	}
	
	@Override
	public Integer call() {
		InputStream is = null;
		ByteArrayOutputStream bos = null;
		try {
			is = FileUtils.openInputStream(sourceFile);
			DAssembler assembler = new DAssembler(is);
			bos = assembler.assemble();
			FileUtils.writeByteArrayToFile(destFile, bos.toByteArray());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return -1;
		} finally {
			try {
				if (is != null)
					is.close();
				if (bos != null)
					bos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}