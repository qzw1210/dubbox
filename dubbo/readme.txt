
�޸�ע�⣺

1) ������µ�ģ�飬Ҫ��POM��maven-shade-plugin��<artifactSet>�����<include>

2) ������µ���չ�㣬Ҫ��POM��maven-shade-plugin����<transformer>

������ ��չ�������ļ� ������

$ find . -wholename */META-INF/dubbo/* -type f | grep -vF /test/ | awk -F/ '{print $NF}' | sort -u
com.alibaba.dubbo.cache.CacheFactory
com.alibaba.dubbo.common.compiler.Compiler
com.alibaba.dubbo.common.extension.ExtensionFactory
...and so on...
