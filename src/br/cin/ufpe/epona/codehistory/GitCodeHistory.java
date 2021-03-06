package br.cin.ufpe.epona.codehistory;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;

import br.cin.ufpe.epona.scmclient.EmptyProjectAtDateException;
import br.cin.ufpe.epona.scmclient.GitClient;
import br.cin.ufpe.epona.util.FileUtil;

public class GitCodeHistory implements CodeHistory {
	
	private static GitCodeHistory instance;
	
	public static GitCodeHistory getInstance() {
		if (instance == null) {
			instance = new GitCodeHistory();
		}
		return instance;
	}
	
	private GitCodeHistory() {	
	
	}
	
	@Override
	public File checkoutToDate(String project, String url, Date date) {
		throw new NoSuchMethodError("Not implemented");
	}
	
	@Override
	public File checkoutToDate(String project, File repositoryFolder, final Date date)
			throws CheckoutException, EmptyProjectAtDateException {
		try { 
			File projectFolder = new File(FileUtil.getInstance().createTempDir(), project);
			FileUtil.getInstance().copyDirectory(repositoryFolder, projectFolder);
			GitClient.getInstance().checkout(projectFolder, date);
			return projectFolder;
		} catch (IOException e) {
			throw new CheckoutException(e);
		} catch (RefAlreadyExistsException e) {
			throw new CheckoutException(e);
		} catch (RefNotFoundException e) {
			throw new CheckoutException(e);
		} catch (InvalidRefNameException e) {
			throw new CheckoutException(e);
		} catch (CheckoutConflictException e) {
			throw new CheckoutException(e);
		} catch (GitAPIException e) {
			throw new CheckoutException(e);
		}
	}

	public static void main(String[] args) throws Exception {
		new GitCodeHistory().checkoutToDate("javacv",
				new File("C:\\Users\\fjsj\\Downloads\\EponaProjects\\playframework"),
				new GregorianCalendar(2012, 5, 23).getTime());
	}
	
}
