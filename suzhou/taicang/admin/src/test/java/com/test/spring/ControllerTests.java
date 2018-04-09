package com.test.spring;

/**
 * @作者 王建明
 * @创建日期 2015-11-19
 * @创建时间 9:36
 * @版本号 V 1.0
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath*:app-servlet.xml","classpath*:app-context.xml"})
public class ControllerTests {
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}

	@Test
	public void controllerExceptionHandler() throws Exception {
		this.mockMvc.perform(get("/console/user/save?userName=%E5%B8%B8%E5%B7%9E%E5%9B%BD%E5%9C%9F1*&password=1&type=MANAGER&status=ENABLED&viewName=%E5%B8%B8%E5%B7%9E%E5%9B%BD%E5%9C%9F1*&userCode=&gender=%E7%94%B7&mobile=&regionCode=320583&description=&caThumbprint=E4146E98CD31A23F98F1F50AD927526E3FDE2384&caNotBeforeTime=2015-06-30 02:25:25&caNotAfterTime=2016-06-30 02:29:08&caCertificate=MIID8DCCA1mgAwIBAgIIIBUGMAAYYoYwDQYJKoZIhvcNAQEFBQAwgY4xDTALBgNVBAYeBABDAE4xDzANBgNVBAgeBmxfgs93ATEPMA0GA1UEBx4GU1dOrF4CMS8wLQYDVQQKHiZsX4LPdwF1NVtQVUZSoYvBTmaLpIvBTi1fw2cJllCNI077UWxT%2BDERMA8GA1UECx4IAEoAUwBDAEExFzAVBgNVBAMeDgBKAFMAQwBBAF8AQwBBMB4XDTE1MDYzMDAyMjUyNVoXDTE2MDYzMDAyMjkwOFowgdIxDjAMBgNVBFgMBTAwMDAxMQ8wDQYDVQQaHgZeAo%2BWUzoxCzAJBgNVBA8eAgAqMRswGQYDVQQBHhIAMQA1ADkANgAzADIAOQA3ADcxGTAXBgNVBC0eEABlAG4AdABDAGUAcgB0ADIxDTALBgNVBAYeBABDAE4xDzANBgNVBAgeBmxfgs93ATEPMA0GA1UEBx4GXjhd3l4CMRMwEQYDVQQKHgpeOF3eVv1XHwAxMQ8wDQYDVQQLHgYAOQA5ADgxEzARBgNVBAMeCl44Xd5W%2FVcfADEwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAK50Y%2FKOui0MxcIB%2F5wBvxp3cGxDF8ju0naHQvZB83U%2FfD1vxuMRs6hryyUHbs%2BBwyStBesXVEzc7hLWPzyJ37vIOQlHgbgVi9buAVaMV6k0mjQ4TS8hB%2Fi7BaUgFH3uhU%2FSiyEaAQFaTT8S%2B2LPJE6RqJDw2hireqLs9Esvu885AgMBAAGjggEPMIIBCzAJBgNVHRMEAjAAMAsGA1UdDwQEAwIGwDAnBgNVHSUEIDAeBggrBgEFBQcDAgYIKwYBBQUHAwQGCCsGAQUFBwMIMB8GA1UdIwQYMBaAFFbAyBFUVTYGSn3tJlDoiL23o3oJMEcGCCsGAQUFBwEBBDswOTA3BggrBgEFBQcwAoYraHR0cDovLzEwLjEwOC41LjI6ODg4MC9kb3dubG9hZC9KU0NBX0NBLmNlcjA%2FBgNVHR8EODA2MDSgMqAwhi5odHRwOi8vd3d3LmpzY2EuY29tLmNuL2NybGRvd25sb2FkL0pTQ0FfQ0EuY3JsMB0GA1UdDgQWBBQSqiub%2BhYkY4O9KwC3FXX4Gti3xTANBgkqhkiG9w0BAQUFAAOBgQB0607cO9j6HONQeGMjkN16mPCNAXcjbU0Cc12yhev6pLq%2FxVErsUFMIpIw5GWLHj%2FzWeOfBwqY70d%2FymzIrqLQsqfgWDPvtXuKWmy2lsPtsr1e4Ac5rAFBtJZwC0iS%2BECunSPYQSSW8ardqh88W0H7Oz%2FirMHkk%2F%2FtfoiEYJtg6w%3D%3D&userId=")).andExpect(status().isOk());
	}
}
