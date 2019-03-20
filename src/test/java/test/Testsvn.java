package test;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class Testsvn {
//20180504--13662	
//	/web (author: 'xieguishi'; revision: 13662; date: Fri May 04 15:56:13 CST 2018)
//	/readme (author: 'liangweiliang'; revision: 13079; date: Tue Apr 24 10:14:35 CST 2018)
//	/version.txt (author: 'cicd'; revision: 13655; date: Fri May 04 15:13:00 CST 2018)
	
//20180403	--12645
//	/config (author: 'wanghongkai'; revision: 12645; date: Mon Apr 16 11:11:10 CST 2018)
//	/web (author: 'chenxiangchun'; revision: 12417; date: Tue Apr 10 11:22:57 CST 2018)
//	/version.txt (author: 'cicd'; revision: 12354; date: Mon Apr 09 10:39:09 CST 2018)
//	/readme (author: 'wanghongkai'; revision: 12645; date: Mon Apr 16 11:11:10 CST 2018)
//	/sql (author: 'wanghongkai'; revision: 12645; date: Mon Apr 16 11:11:10 CST 2018)
//	/20180405 (author: 'dengfengxiang'; revision: 12284; date: Thu Apr 05 15:22:34 CST 2018)
	
//	20180404--12283
//	/web (author: 'liangweiliang'; revision: 12279; date: Wed Apr 04 18:31:02 CST 2018)
//	/version.txt (author: 'cicd'; revision: 12283; date: Wed Apr 04 20:10:09 CST 2018)
//	/readme (author: 'chenxiangchun'; revision: 12229; date: Wed Apr 04 10:45:59 CST 2018)
	public static void main(String[] args) {
		String url = "http://172.20.21.1/svn/2018080611";
		String path="/20180806_银行存管/version.txt";
		String name = "luojuwang";
		String password = "luojuwang";

//		System.out.println(getFileContent(url, name, password, path, 0));
		setupLibrary();

		SVNRepository repository = null;

		try {

			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));

		} catch (SVNException svne) {

			System.err.println("error while creating an SVNRepository for location '"

			+ url + "': " + svne.getMessage());

			System.exit(1);

		}

		ISVNAuthenticationManager authManager = SVNWCUtil

		.createDefaultAuthenticationManager(name, password);

		repository.setAuthenticationManager(authManager);

		try {

			SVNNodeKind nodeKind = repository.checkPath("", -1);

			if (nodeKind == SVNNodeKind.NONE) {

				System.err.println("There is no entry at '" + url + "'.");

				System.exit(1);

			} else if (nodeKind == SVNNodeKind.FILE) {

				System.err.println("The entry at '" + url

				+ "' is a file while a directory was expected.");

				System.exit(1);

			}

			System.out.println("Repository Root: "

			+ repository.getRepositoryRoot(true));

			System.out.println("Repository UUID: "

			+ repository.getRepositoryUUID(true));

			System.out.println("");

			listEntries(repository, "");

		} catch (SVNException svne) {

			System.err.println("error while listing entries: "

			+ svne.getMessage());

			System.exit(1);

		}

		long latestRevision = -1;

		try {

			latestRevision = repository.getLatestRevision();

		} catch (SVNException svne) {

			System.err

			.println("error while fetching the latest repository revision: "

			+ svne.getMessage());

			System.exit(1);

		}

		System.out.println("");

		System.out.println("---------------------------------------------");

		System.out.println("Repository latest revision: " + latestRevision);

		System.exit(0);

	}

	private static void setupLibrary() {

		DAVRepositoryFactory.setup();

		SVNRepositoryFactoryImpl.setup();

		FSRepositoryFactory.setup();

	}

	public static void listEntries(SVNRepository repository, String path) throws SVNException {
		Collection entries = repository.getDir(path, -1, null, (Collection) null);

		Iterator iterator = entries.iterator();

		while (iterator.hasNext()) {

			SVNDirEntry entry = (SVNDirEntry) iterator.next();
//			if(entry.getName().equals("version.txt")){
				System.out.println("/" + (path.equals("") ? "" : path + "/")

						+ entry.getName() + " (author: '" + entry.getAuthor()

						+ "'; revision: " + entry.getRevision() + "; date: "

						+ entry.getDate() +entry.getURL()+ ")");
				
				String fileName=entry.getURL().toString();				
				System.out.println(fileName+"-----------------");
				
//			}
	

		}

	}
	/**
	 * 获取指定路径文件在某个版本的内容
	 * 
	 * @param url
	 *            svn库URL
	 * @param userName
	 *            svn库用户名
	 * @param password
	 *            svn库密码
	 * @param path
	 *            指定路径
	 * @param revision
	 *            起始版本(0表示最新版本)
	 * @return null:指定路径不存在或文件已删除或非文本文件
	 */
	public static String getFileContent(String url, String userName, String password, String path, long revision){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			SVNURL repositoryURL = SVNURL.parseURIEncoded(url);
			SVNRepository repository = SVNRepositoryFactory.create(repositoryURL);
			@SuppressWarnings("deprecation")
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, password.toCharArray());
			repository.setAuthenticationManager(authManager);

			SVNNodeKind nodeKind = repository.checkPath(path, revision);
			if (nodeKind != SVNNodeKind.FILE) {
				return null;
			}
			Map<String, String> fileProperties = new HashMap<>();
			repository.getFile(path, revision, SVNProperties.wrap(fileProperties), baos);
			if (!SVNProperty.isTextMimeType(fileProperties.get(SVNProperty.MIME_TYPE))) {
				return null;
			}

			return baos.toString();
		} catch (SVNException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
