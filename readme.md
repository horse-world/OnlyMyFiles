# 用于在git中只展示本人参与的commit记录的脚本
## 1. 项目初始化
https://www.cnblogs.com/moutory/p/17752679.html

git push -f origin <branch_name>

<!-- Plugin Configuration File. Read more: https://plugins.jetbrains.com/docs/intellij/plugin-configuration-file.html -->
```xml

<idea-plugin>
    <!-- Unique identifier of the plugin. It should be FQN. It cannot be changed between the plugin versions. -->
    <id>org.ma5d.OnlyMyFiles</id>

    <!-- Public plugin name should be written in Title Case.
         Guidelines: https://plugins.jetbrains.com/docs/marketplace/plugin-overview-page.html#plugin-name -->
    <name>OnlyMyFiles</name>

    <!-- A displayed Vendor name or Organization ID displayed on the Plugins Page. -->
    <vendor email="support@yourcompany.com" url="https://www.yourcompany.com">YourCompany</vendor>

    <!-- Description of the plugin displayed on the Plugin Page and IDE Plugin Manager.
         Simple HTML elements (text formatting, paragraphs, and lists) can be added inside of <![CDATA[ ]]> tag.
         Guidelines: https://plugins.jetbrains.com/docs/marketplace/plugin-overview-page.html#plugin-description -->
    <description><![CDATA[
    Enter short description for your plugin here.<br>
    <em>most HTML tags may be used</em>
]]></description>

    <!-- Product and plugin compatibility requirements.
         Read more: https://plugins.jetbrains.com/docs/intellij/plugin-compatibility.html -->
    <depends>com.intellij.modules.platform</depends>

    <!-- Extension points defined by the plugin.
         Read more: https://plugins.jetbrains.com/docs/intellij/plugin-extension-points.html -->
    <extensions defaultExtensionNs="com.intellij">

    </extensions>
    <actions>
        <action id="FilterProjectFilesAction" class="org.ma5d.onlymyfiles.FilterProjectFilesAction"
                text="Filter Project Files" description="Filter files in the project view">
            <add-to-group group-id="ProjectViewPopupMenu" anchor="last"/>
        </action>
    </actions>
</idea-plugin>
```