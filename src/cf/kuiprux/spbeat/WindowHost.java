package cf.kuiprux.spbeat;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

public class WindowHost extends AppGameContainer implements AutoCloseable {
	
	//�⺻ â ũ��. ���� ����
	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;

	public WindowHost(Game game) throws SlickException {
		super(game);
		
		init();
	}
	
	//���� �� â ���� ��� �ʱ�ȭ
	private void init() throws SlickException {
		setDisplayMode(WIDTH, HEIGHT, false);
	}
	
	//���۽� ȣ��
	@Override
	public void start() throws SlickException {
		super.start();
	}

	//������ �ڵ� ȣ��
	@Override
	public void close() throws Exception {
		
	}

}
