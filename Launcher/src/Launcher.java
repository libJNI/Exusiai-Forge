

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class Launcher {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			if (!checkOs()) {
				JOptionPane.showMessageDialog(dialog, "Some error", "恩特人", 1);
				return;
			}

			(new Launcher()).inject();
		} catch (Exception var2) {
			var2.printStackTrace();
			exit();
		}

	}

	private static boolean checkOs() {
		return System.getProperty("os.name").toLowerCase().startsWith("windows") && System.getProperty("os.arch").contains("64");
	}

	private static String encrypt(String s) {
		StringBuilder encrypt = new StringBuilder();

		byte[] bytes;
		int length = (bytes = s.getBytes(StandardCharsets.UTF_8)).length;

		for(int i = 0; i < length; ++i) {
			byte b = bytes[i];
			encrypt.append(String.valueOf(b)).append("%");
		}

		encrypt.reverse();
		return encrypt.toString().replace("0", "X").replace("1", "Y");
	}

	private static void exit() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM " + ManagementFactory.getRuntimeMXBean().getName().substring(0, ManagementFactory.getRuntimeMXBean().getName().indexOf("@")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}

	private void inject() {
		File dllFile = new File(System.getProperty("user.dir") + "\\attach.dll");
		if (!dllFile.exists()) {
			this.error("没找到attach.dll 正确路径:" + dllFile.getAbsolutePath());
		} else {
			this.initDll(dllFile);
			VirtualMachineDescriptor attach_ = VirtualMachine.list().stream().filter((m) -> m.displayName().startsWith("net.minecraft.launchwrapper.Launch")).findFirst().orElse(null);
			if (attach_ == null) {
				this.error("找不到Minecraft");
			} else {
				try {
					VirtualMachine attach = VirtualMachine.attach(attach_);
					File agentJar = File.createTempFile(".mat-debug-", (Math.random() > 0.5D ? ".log" : ".ini"));
					File injectorJar = new File(Launcher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
					File clientJar = new File(System.getProperty("user.dir") + "\\build\\libs\\unnamed.jar");
					if (!clientJar.exists()) {
						this.error("找不到外挂(Client.jar)");
						this.error("正确路径: " + System.getProperty("user.dir") + "\\build\\libs\\Client-1.0.jar");
						return;
					}

					Files.copy(clientJar.toPath(), agentJar.toPath(), StandardCopyOption.REPLACE_EXISTING);
					this.clearTemp(agentJar.getParentFile(), agentJar);
					this.time(agentJar);

					try {
						attach.loadAgent(agentJar.getAbsolutePath(), encrypt(injectorJar.getAbsolutePath()));
					} catch (Exception var8) {
						this.error(var8.getMessage());
						exit();
					}

					attach.detach();
					this.error("额,应该注入好了");
					exit();
				} catch (Exception var9) {
					var9.printStackTrace();
					exit();
				}

			}
		}
	}

	private void clearTemp(File folder, File x) {
		File[] listFiles;
		int length = (Objects.requireNonNull(listFiles = folder.listFiles())).length;

		for(int i = 0; i < length; ++i) {
			File f = listFiles[i];
			if (!f.getName().equals(x.getName()) && f.getName().startsWith("+~") && !f.isDirectory() && !f.isHidden() && f.getName().toLowerCase().endsWith(".tmp")) {
				f.delete();
			}
		}

	}

	private void time(File file) {
		try {
			long time_milis = System.currentTimeMillis() - ((Math.random() > 0.5D ? TimeUnit.DAYS.toMillis(1L) : 0L) + TimeUnit.HOURS.toMillis(ThreadLocalRandom.current().nextInt(1, 23)) + TimeUnit.MINUTES.toMillis(ThreadLocalRandom.current().nextInt(1, 118)));
			BasicFileAttributeView attributes = Files.getFileAttributeView(Paths.get(file.getAbsolutePath()), BasicFileAttributeView.class);
			FileTime time = FileTime.fromMillis(time_milis);
			attributes.setTimes(time, time, time);
		} catch (Exception var6) {
		}

	}

	private void initDll(File dll) {
		try {
			if (System.getProperty("java.library.path") != null) {
				System.setProperty("java.library.path", dll.getAbsolutePath() + System.getProperty("path.separator") + System.getProperty("java.library.path"));
			} else {
				System.setProperty("java.library.path", dll.getAbsolutePath());
			}

			Field declaredField = ClassLoader.class.getDeclaredField("sys_paths");
			declaredField.setAccessible(true);
			declaredField.set(null, null);
			System.loadLibrary("attach");
		} catch (Exception var3) {
			var3.printStackTrace();
			exit();
		}

	}

	private static void copy(InputStream source, String destination) {
		try {
			Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException var3) {
		}

	}

	private void error(String message) {
		JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message, "恩特人", 1);
	}
}
