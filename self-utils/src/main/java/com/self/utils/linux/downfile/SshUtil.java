package com.self.utils.linux.downfile;

import com.jcraft.jsch.*;

import java.util.Properties;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2021-12-02 18:26:11
 */

public class SshUtil {
    private ChannelSftp channelSftp;
    private ChannelExec channelExec;
    private Session session = null;
    private int timeout = 60000;

    public SshUtil(SshConfiguration conf) throws JSchException {
        System.out.println("try connect to  " + conf.getHost() + ",username: " + conf.getUserName() + ",password: " + conf.getPassword() + ",port: " + conf.getPort());
        JSch jSch = new JSch(); //创建JSch对象
        session = jSch.getSession(conf.getUserName(), conf.getHost(), conf.getPort());//根据用户名，主机ip和端口获取一个Session对象
        session.setPassword(conf.getPassword()); //设置密码
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);//为Session对象设置properties
        session.setTimeout(timeout);//设置超时
        session.connect();//通过Session建立连接
    }

    /**
     * 下载文件
     * @param src linux服务器文件地址
     * @param dst dst 本地存放地址
     * @throws JSchException
     * @throws SftpException
     */
    public void download(String src, String dst) throws JSchException, SftpException {
        channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        channelSftp.get(src, dst);
        channelSftp.quit();
    }

    /**
     * 上传文件
     * @param src 本机文件地址
     * @param dst 远程文件地址
     * @throws JSchException
     * @throws SftpException
     */
    public void upLoad(String src, String dst) throws JSchException, SftpException {
        channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        channelSftp.put(src, dst);
        channelSftp.quit();
    }

    public void close() {
        session.disconnect();
    }

    public static void main(String[] args) {
        SshConfiguration configuration = new SshConfiguration();
        configuration.setHost("172.17.1.232");
        configuration.setUserName("root");
        configuration.setPassword("root275858");
        configuration.setPort(22);
        try {
//            SshUtil sshUtil=new SshUtil(configuration);
//            sshUtil.download("/home/cafintech/Logs/metaData/meta.log","D://meta.log");
//            sshUtil.close();
//            System.out.println("文件下载完成");
            SshUtil sshUtil = new SshUtil(configuration);
            sshUtil.upLoad("D://meta.log", "/home/cafintech/");
            sshUtil.close();
            System.out.println("文件上传完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
