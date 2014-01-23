local component = require("component")
local fs = require("filesystem")
local shell = require("shell")

local dirs = shell.parse(...)
if #dirs == 0 then
  print("Usage: ln <target> [<name>]")
  return
end

local target = shell.resolve(dirs[1])
local linkpath
if #dirs > 1 then
  linkpath = shell.resolve(dirs[2])
else
  linkpath = fs.concat(shell.getWorkingDirectory(), fs.name(target))
end

local result, reason = fs.link(target, linkpath)
if not result then
  print(reason)
end