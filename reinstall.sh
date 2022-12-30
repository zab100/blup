powershell.exe Start-Process "powershell  \" Get-AppxPackage -all *android* | Remove-AppxPackage -AllUsers\"" -Wait -Verb runas 
windowspath=$(wslpath -w $1)
powershell.exe Start-Process "powershell  \" Add-AppxPackage -Register '$windowspath\\AppxManifest.xml' -ForceUpdateFromAnyVersion\"" -Wait -Verb runas 