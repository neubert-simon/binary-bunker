# PowerShell-Skript for our Windows-User :)
Clear-Host
Write-Host "-------DB Build script-------"
Get-Date

# Docker starten
try {
& "$env:ProgramFiles\Docker\Docker\Docker Desktop.exe"
} catch {
echo "Docker Desktop is not installed in the default directory. Please manually start the App and try again if the script fails."
}

# Auf Docker-Start warten
Start-Sleep -Seconds 10

# Neueste PostgreSQL-Version abrufen
docker pull postgres:l

# Docker-Image mit docker-compose erstellen
docker-compose build


# Container starten (-d = läuft im Hintergrund)
docker-compose up -d


# Region: Verfügbarkeit des Docker-Containers prüfen
$ContainerId = docker inspect -f '{{.Id}}' BinaryBunker-postgres 2>&1