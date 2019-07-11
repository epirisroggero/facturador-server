<#macro card title subtitles dataArray>

<details class="detail-main">
    <summary>${title}</summary>
    <div style="padding-left: 2em;">
        <div>
            <#list subtitles as s>
            ${s}<#sep>, </#sep>
            </#list>
        </div>
        <div>
            <#list dataArray as s>
            ${s}<#sep>, </#sep>
            </#list>
        </div>
    </div>
</details>
</#macro>


