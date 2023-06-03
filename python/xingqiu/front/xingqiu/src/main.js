const { app, BrowserWindow } = require('electron');

function createWindow() {
  // 创建浏览器窗口
  const win = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      nodeIntegration: true
    }
  });

  // 加载应用主页面
  win.loadFile('dist/index.html');

  // 打开开发者工具
  win.webContents.openDevTools();
}

// 当 Electron 初始化完成时，创建浏览器窗口
app.whenReady().then(createWindow);

// 当所有窗口关闭时，退出应用
app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});